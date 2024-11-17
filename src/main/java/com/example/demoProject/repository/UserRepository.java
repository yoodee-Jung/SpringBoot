package com.example.demoProject.repository;

import com.example.demoProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email); // 이메일 중복 확인
    boolean existsByNickname(String nickname); // 닉네임 중복 확인
}