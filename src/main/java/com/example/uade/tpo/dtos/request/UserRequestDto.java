package com.example.uade.tpo.dtos.request;

import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserRequestDto {
    String username;
    String firstname;
    String lastName;
    String email;
    String password;
}
