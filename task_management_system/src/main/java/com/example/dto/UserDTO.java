package com.example.dto;

import com.example.model.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "email ID is required")
    private String emailId;

    public static User toEntity(UserDTO userDTO){
        return User.builder()
                .username(userDTO.getUsername())
                .emailId(userDTO.getEmailId())
                .build();
    }

    public static UserDTO toUserDTO(User user){
        return UserDTO.builder()
                .username(user.getUsername())
                .emailId(user.getEmailId())
                .build();
    }
}
