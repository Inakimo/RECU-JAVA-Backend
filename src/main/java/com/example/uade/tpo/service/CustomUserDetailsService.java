package com.example.uade.tpo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.uade.tpo.entity.User;
import com.example.uade.tpo.repository.IUserRepository;

public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository repository;

    public CustomUserDetailsService(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username).get();

        if(user == null){
            throw new UsernameNotFoundException("User not found");

        }

        return repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }
}
