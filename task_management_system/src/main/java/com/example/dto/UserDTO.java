package com.example.dto;

import com.example.model.Users;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "email ID is required")
    private String emailId;

    public static Users toEntity(UserDTO userDTO){
        return Users.builder()
                .username(userDTO.getUsername())
                .emailId(userDTO.getEmailId())
                .build();
    }

    public static UserDTO toUserDTO(Users user){
        return UserDTO.builder()
                .username(user.getUsername())
                .emailId(user.getEmailId())
                .build();
    }
}
