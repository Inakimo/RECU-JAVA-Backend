package com.example.uade.tpo.controller.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.uade.tpo.dtos.request.ChangeRoleRequestDto;
import com.example.uade.tpo.dtos.request.UserDeleteRequestDto;
import com.example.uade.tpo.dtos.response.UserResponseDto;
import com.example.uade.tpo.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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

    @DeleteMapping("/{adminID}")
    public ResponseEntity<?> deleteUser(@PathVariable long adminID,@RequestBody UserDeleteRequestDto userId ){
        userService.deleteUser(adminID,userId.getUserDeletionId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/{adminId}")
    public ResponseEntity<List<UserResponseDto>> getUser(@PathVariable Long adminId) {
        List<UserResponseDto> user = userService.getUsers(adminId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}