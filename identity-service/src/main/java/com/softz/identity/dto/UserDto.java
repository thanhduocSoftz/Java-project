package com.softz.identity.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    
}
