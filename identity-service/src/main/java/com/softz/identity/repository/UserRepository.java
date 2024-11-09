package com.softz.identity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.softz.identity.entity.User;
import com.softz.identity.entity.projection.UserBasicInfo;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String username);

    Optional<UserBasicInfo> findUserBasicInfoByUsername(String username);

    void deleteByUsername(String s);
}
