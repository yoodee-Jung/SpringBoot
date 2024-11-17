package com.example.demoProject.entity;

import com.example.demoProject.dto.UserDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_tb")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 자동 생성
    @Column(name = "user_index")
    private Long userIndex; // 사용자 고유 식별자

    @Column(nullable = false, unique = true, length = 100) // 이메일은 고유값
    private String email;

    @Column(nullable = false, unique = true, length = 50) // 닉네임은 고유값
    private String nickname;

    @Column(nullable = false, length = 255) // 패스워드는 암호화된 값으로 저장
    private String passwd;

    @Column(nullable = false, length = 255) // 보안 질문
    private String question;

    @Column(nullable = false, length = 255) // 보안 질문에 대한 답변
    private String answer;

    private User convertToEntity(UserDto dto) {
        User user = new User();
        user.setUserIndex(dto.getUserIndex());
        user.setEmail(dto.getEmail());
        user.setNickname(dto.getNickname());
        return user;
    }
}

// 위 엔티티로부터 생성되는 데이터베이스 테이블 스키마는 다음과 같습니다:
//CREATE TABLE users (
//        user_index BIGINT AUTO_INCREMENT PRIMARY KEY,
//        email VARCHAR(100) NOT NULL UNIQUE,
//nickname VARCHAR(50) NOT NULL UNIQUE,
//passwd VARCHAR(255) NOT NULL,
//question VARCHAR(255) NOT NULL,
//answer VARCHAR(255) NOT NULL
//);