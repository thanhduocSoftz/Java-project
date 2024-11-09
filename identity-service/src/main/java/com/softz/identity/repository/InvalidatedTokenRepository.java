package com.softz.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softz.identity.entity.InvalidatedToken;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {}
