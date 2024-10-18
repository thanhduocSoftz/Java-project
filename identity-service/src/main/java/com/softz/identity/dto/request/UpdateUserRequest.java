package com.softz.identity.dto.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dob;
}
