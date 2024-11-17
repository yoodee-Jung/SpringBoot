package com.example.demoProject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "feedback_tb")
@Getter
@Setter
@NoArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 자동 생성
    @Column(name = "feedback_index")
    private Long feedbackIndex; // 피드백 인덱스 (PK)

    @ManyToOne // 다대일 관계 (여러 피드백이 하나의 유저를 참조)
    @JoinColumn(name = "user_index", nullable = false) // 외래 키 설정
    private User user; // 유저 인덱스 (FK)

    @Column(nullable = false, length = 1000) // 피드백 내용
    private String content;

    @Column(length = 1000) // 피드백 답변
    private String answer;
}

//위 엔티티로부터 생성되는 데이터베이스 테이블 스키마는 다음과 같습니다:
//CREATE TABLE feedback_tb (
//        feedback_index BIGINT AUTO_INCREMENT PRIMARY KEY,
//        user_index BIGINT NOT NULL,
//        content VARCHAR(1000) NOT NULL,
//answer VARCHAR(1000),
//FOREIGN KEY (user_index) REFERENCES users(user_index)
//        );
