package com.example.uade.tpo.service;

import com.example.uade.tpo.Utils.Mapper;
import com.example.uade.tpo.dtos.request.ChangeRoleRequestDto;
import com.example.uade.tpo.dtos.request.UserRequestDto;
import com.example.uade.tpo.dtos.response.UserResponseDto;
import com.example.uade.tpo.entity.Role;
import com.example.uade.tpo.entity.User;
import com.example.uade.tpo.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    public List<UserResponseDto> getUsers() {
        return userRepository.findAll().stream().map(Mapper::convertToUserResponseDto).collect(Collectors.toList());
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

    public Boolean deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public void changeRole(ChangeRoleRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setRole(Role.valueOf(request.getNewRole()));
        userRepository.save(user);
    }
}
