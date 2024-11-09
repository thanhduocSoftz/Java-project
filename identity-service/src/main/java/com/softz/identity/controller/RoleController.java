package com.softz.identity.controller;

import org.springframework.web.bind.annotation.*;

import com.softz.identity.dto.ApiResponse;
import com.softz.identity.dto.PageData;
import com.softz.identity.dto.RoleDto;
import com.softz.identity.dto.request.NewRoleRequest;
import com.softz.identity.service.RoleService;
import com.softz.identity.service.coordinator.RoleCoordinatorService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleCoordinatorService roleCoordinatorService;
    RoleService roleService;

    @PostMapping("/roles")
    public ApiResponse<RoleDto> createRole(@RequestBody NewRoleRequest newRoleRequest) {
        var roleDto = roleCoordinatorService.createRole(newRoleRequest);
        return ApiResponse.<RoleDto>builder().result(roleDto).build();
    }

    @GetMapping("/roles")
    public ApiResponse<PageData<RoleDto>> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        PageData<RoleDto> roles = roleService.getAll(page, size);
        return ApiResponse.<PageData<RoleDto>>builder().result(roles).build();
    }
}
