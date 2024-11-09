package com.softz.identity.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.softz.identity.dto.PageData;
import com.softz.identity.dto.UserDto;
import com.softz.identity.entity.User;
import com.softz.identity.exception.AppException;
import com.softz.identity.exception.ErrorCode;
import com.softz.identity.mapper.UserMapper;
import com.softz.identity.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;

    public User create(User user) {
        return userRepository.save(user);
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_ID_NOT_FOUND, userId));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    public UserDto getMyProfile() {
        // lay username dang dang nhap bang SecurityContextHolder
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        var user = userRepository
                .findUserBasicInfoByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserDto(user);
    }

    public int unitTest(int a, int b){
        return  a + b;
    }

    // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PreAuthorize("hasRole('ADMIN')")
    public PageData<UserDto> getUsers(int page, int size, String keyword) {

        Specification<User> query = (root, query1, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (org.springframework.util.StringUtils.hasText(keyword)) {
                var criteria1 = criteriaBuilder.or(
                        criteriaBuilder.like(root.get("username"), "%" + keyword + "%"),
                        criteriaBuilder.like(root.get("email"), "%" + keyword + "%")
                        // criteriaBuilder.like(root.get("lastName"), "%" + keyword + "%")
                        );

                predicates.add(criteria1);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("username").ascending());

        var pageData = userRepository.findAll(query, pageable);

        return PageData.<UserDto>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(page)
                .totalElements(pageData.getTotalElements())
                .data(pageData.stream().map(userMapper::toUserDto).toList())
                .build();
    }
}
