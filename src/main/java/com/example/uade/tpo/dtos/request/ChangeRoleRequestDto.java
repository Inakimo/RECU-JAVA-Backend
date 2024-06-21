package com.example.uade.tpo.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeRoleRequestDto {
    @NotBlank
    private String email;
    @NotBlank
    private String newRole;
}
