package com.softz.identity.controller;

import java.util.List;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import com.softz.identity.dto.ApiResponse;
import com.softz.identity.dto.PermissionDto;
import com.softz.identity.dto.request.NewPermissionRequest;
import com.softz.identity.dto.request.UpdatePermissionRequest;
import com.softz.identity.service.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping("/permissions")
    ApiResponse<PermissionDto> createPermission(@RequestBody @Valid NewPermissionRequest request) {
        log.info("gydsg");
        var result = permissionService.createPermission(request);

        return ApiResponse.<PermissionDto>builder().result(result).build();
    }

    @GetMapping("/permissions")
    ApiResponse<List<PermissionDto>> getPermissions() {
        var result = permissionService.getPermissions();

        return ApiResponse.<List<PermissionDto>>builder().result(result).build();
    }

    @DeleteMapping("/permissions/{id}")
    ApiResponse<Void> deletePermission(@PathVariable int id) {
        permissionService.deletePermission(id);
        return ApiResponse.<Void>builder().build();
    }

    @PutMapping("/permissions/{id}")
    ApiResponse<PermissionDto> updatePermission(@PathVariable int id, @RequestBody UpdatePermissionRequest request) {
        var result = permissionService.updatePermission(id, request);
        return ApiResponse.<PermissionDto>builder().result(result).build();
    }
}
