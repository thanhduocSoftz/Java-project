package com.softz.identity.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.softz.identity.dto.PageData;
import com.softz.identity.dto.RoleDto;
import com.softz.identity.entity.Role;
import com.softz.identity.mapper.RoleMapper;
import com.softz.identity.repository.RoleRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;

    public PageData<RoleDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("name").ascending());
        var pageData = roleRepository.findAll(pageable);

        return PageData.<RoleDto>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(page)
                .totalElements(pageData.getTotalElements())
                .data(pageData.stream().map(roleMapper::toRoleDto).toList())
                .build();
        // roleRepository
        //         .findAll()
        //         .stream()
        //         .map(roleMapper::toRoleDto)
        //         .toList();
    }

    public Role create(Role role) {
        return roleRepository.save(role);
    }

    public List<Role> getRoles(List<Integer> roles) {
        return roleRepository.findByIdIn(roles);
    }
}
