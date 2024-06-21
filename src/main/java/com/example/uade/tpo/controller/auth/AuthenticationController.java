package com.example.uade.tpo.controller.auth;

import com.example.uade.tpo.dtos.request.ChangeRoleRequestDto;
import com.example.uade.tpo.dtos.response.UserResponseDto;
import com.example.uade.tpo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private AuthenticationService service;
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/change_role")
    public ResponseEntity<Void> changeRole(@RequestBody ChangeRoleRequestDto request) {
        userService.changeRole(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUser() {
        List<UserResponseDto> user = userService.getUsers();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}