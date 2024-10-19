package com.softz.identity.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dob;
    private List<RoleDto> roles;

    
}
