package com.softz.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.softz.identity.dto.UserDto;
import com.softz.identity.dto.request.NewUserRequest;
import com.softz.identity.entity.User;
import com.softz.identity.entity.projection.UserBasicInfo;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "userId", source = "id")
    UserDto toUserDto(User user);

    @Mapping(target = "roles", ignore = true)
    User toUser(NewUserRequest request);

    @Mapping(target = "userId", source = "id")
    UserDto toUserDto(UserBasicInfo basicInfo);
}
