package com.appfullstack.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appfullstack.backend.entities.PasswordRecover;

public interface PasswordRecoverRepository extends JpaRepository<PasswordRecover, Long> {

}
