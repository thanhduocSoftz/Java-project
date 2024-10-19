package com.softz.identity.service;

import com.softz.identity.dto.UserDto;
import com.softz.identity.dto.request.NewUserRequest;
import com.softz.identity.dto.request.UpdateUserRequest;
import com.softz.identity.entity.Role;
import com.softz.identity.entity.User;
import com.softz.identity.exception.AppException;
import com.softz.identity.exception.ErrorCode;
import com.softz.identity.mapper.UserMapper;
import com.softz.identity.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRolesCoordinatorService roleCoordinateService;
    UserRepository userRepository;
    UserMapper userMapper;

    public UserDto createUser(NewUserRequest userRequest) {
        try {
            List<Role> roles = roleCoordinateService.roleService.findByIdIn(userRequest.getRoles());
            if (!roles.isEmpty() && userRequest.getRoles().size() != roles.size()) {
                throw new AppException(ErrorCode.INVALID_INPUT);
            }

            User user = userMapper.toUser(userRequest);
            user.setRoles(Set.copyOf(roles));
            return userMapper.toUserDto(userRepository.save(user));
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
    }

    public UserDto getUserById(String userId) {
        return userRepository.findById(userId)
                .map(userMapper::toUserDto)
                .orElseThrow(() -> new AppException(ErrorCode.USER_ID_NOT_FOUND, userId));
    }
    
    public UserDto getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toUserDto)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }
    

    public List<UserDto> getUsers() {
        return userRepository.findAll()
            .stream()
            .map(userMapper::toUserDto)
            .toList();
    }

    public void deleteUser(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_ID_NOT_FOUND, userId);
        }
        userRepository.deleteById(userId);
    }
    

    public UserDto updateUser(String userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_ID_NOT_FOUND, userId));
    
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());
    
        User updatedUser = userRepository.save(user);
    
        return userMapper.toUserDto(updatedUser);
    }
    
}
