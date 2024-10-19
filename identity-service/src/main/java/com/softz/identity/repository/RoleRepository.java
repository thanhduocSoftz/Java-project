package com.softz.identity.repository;

import com.softz.identity.entity.Role;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
    List<Role> findByIdIn(List<Integer> ids);
}