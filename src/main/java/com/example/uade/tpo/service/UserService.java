package com.example.uade.tpo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.uade.tpo.Utils.Mapper;
import com.example.uade.tpo.dtos.request.ChangeRoleRequestDto;
import com.example.uade.tpo.dtos.request.UserRequestDto;
import com.example.uade.tpo.dtos.response.UserResponseDto;
import com.example.uade.tpo.entity.Role;
import com.example.uade.tpo.entity.User;
import com.example.uade.tpo.repository.IUserRepository;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    public List<UserResponseDto> getUsers(long userId) {
        if (isAdmin(userId)) {
            return userRepository.findAll().stream().map(Mapper::convertToUserResponseDto).collect(Collectors.toList());
        }else{
            return null;
        }
        
    }

    public Optional<UserResponseDto> getUserById(Long userId) {
        return userRepository.findById(userId).map(Mapper::convertToUserResponseDto);
    }

    public UserResponseDto createUser(UserRequestDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            return null;
        }
        User user = new User();
        user.setUserName(userDto.getUsername());
        user.setFirstName(userDto.getFirstname());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return Mapper.convertToUserResponseDto(userRepository.save(user));
    }

    public UserResponseDto updateUser(Long userId, UserRequestDto userDetails) {
        if (userRepository.findByEmail(userDetails.getEmail()).isPresent()) {
            return null;
        }
        return userRepository.findById(userId).map(user -> {
            user.setUserName(userDetails.getUsername());
            user.setFirstName(userDetails.getFirstname());
            user.setLastName(userDetails.getLastName());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            return Mapper.convertToUserResponseDto(userRepository.save(user));
        }).orElse(null);
    }
    

    public boolean isAdmin(Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()){
            return false;
        }

        User user = userOptional.get();
        if(user.getRole() == Role.ROLE_ADMIN){
            return true;
        }

        return false;
    }

    public boolean isSeller(Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()){
            return false;
        }

        User user = userOptional.get();
        if(user.getRole() == Role.ROLE_SELLER){
            return true;
        }

        return false;
    }


    public Boolean deleteUser(Long adminID,Long userId) {
        Optional<User> admin = userRepository.findById(adminID);
        if (admin.get().getRole().equals(Role.ROLE_ADMIN)){
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
                return true;
            }
            return false;
        }else{
            return false;
        }
        
    }

    public void changeRole(ChangeRoleRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setRole(Role.valueOf(request.getNewRole()));
        userRepository.save(user);
    }
}
