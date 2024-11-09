package com.softz.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.softz.identity.dto.PermissionDto;
import com.softz.identity.dto.request.NewPermissionRequest;
import com.softz.identity.dto.request.UpdatePermissionRequest;
import com.softz.identity.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionDto toPermissionDto(Permission permission);

    Permission toPermission(NewPermissionRequest request);

    void updatePermission(@MappingTarget Permission permission, UpdatePermissionRequest request);
}
