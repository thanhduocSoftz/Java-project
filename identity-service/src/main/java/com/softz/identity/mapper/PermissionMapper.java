package com.softz.identity.mapper;

import com.softz.identity.dto.PermissionDto;
import com.softz.identity.dto.request.NewPermissionRequest;
import com.softz.identity.dto.request.UpdatePermissionRequest;
import com.softz.identity.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionDto toPermissionDto(Permission permission);
    Permission toPermission(NewPermissionRequest newPermissionRequest);
    Permission toPermission(UpdatePermissionRequest updatePermissionRequest);

}