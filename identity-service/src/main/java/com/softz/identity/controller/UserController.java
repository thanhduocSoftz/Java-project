package com.softz.identity.controller;

import jakarta.validation.Valid;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import com.softz.identity.dto.ApiResponse;
import com.softz.identity.dto.PageData;
import com.softz.identity.dto.UserDto;
import com.softz.identity.dto.request.NewUserRequest;
import com.softz.identity.entity.User;
import com.softz.identity.service.UserService;
import com.softz.identity.service.coordinator.UserCoordinatorService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserCoordinatorService userCoordinatorService;
    UserService userService;

    @PostMapping("/users/registration")
    public ApiResponse<UserDto> createUser(@RequestBody @Valid NewUserRequest newUserRequest) {
        var userDto = userCoordinatorService.createUser(newUserRequest);
        return ApiResponse.<UserDto>builder().result(userDto).build();
    }

    @GetMapping("/users")
    public ApiResponse<PageData<UserDto>> getUsers(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "keyword", required = false) String keyword) {
        // Authentication authentication = SecurityContextHolder.
        // getContext().
        // getAuthentication();

        PageData<UserDto> users = userService.getUsers(page, size, keyword);
        return ApiResponse.<PageData<UserDto>>builder().result(users).build();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") String userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/users/filter")
    public User getUserByUsername(@Param("username") String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/users/me")
    public ApiResponse<UserDto> getMyProfile() {
        UserDto userDto = userService.getMyProfile();
        return ApiResponse.<UserDto>builder().result(userDto).build();
    }
}
