package com.kernel.member.repository;

import com.kernel.member.domain.entity.Manager;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long>, CustomManagerRepository {
}
