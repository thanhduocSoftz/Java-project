package com.softz.identity.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softz.identity.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findByIdIn(Collection<Integer> ids);
}
