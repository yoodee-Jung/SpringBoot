package com.example.demoProject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "print_tb")
@Getter
@Setter
@NoArgsConstructor
public class Print {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 자동 생성
    @Column(name = "print_index")
    private Long printIndex; // 출력 인덱스 (PK)

    @ManyToOne // 다대일 관계 (여러 출력 기록이 하나의 유저를 참조)
    @JoinColumn(name = "user_index", nullable = false) // 외래 키 설정
    private User user; // 유저 인덱스 (FK)

    @Column(nullable = false) // 레이어 수
    private Integer layer;

    @Lob // 대용량 데이터(이미지 파일) 처리
    @Column(name = "actual_print", nullable = true)
    private byte[] actualPrint; // 실제 출력 사진

    @Column(name = "is_normal", nullable = false)
    private Integer isNormal; // 출력 정상 여부 (불량 발생 시 종류)

    @Column(name = "match_rate", nullable = false)
    private Float matchRate; // 출력물 일치율

    @Lob // 대용량 데이터(이미지 파일) 처리
    @Column(name = "expected_print_modeling", nullable = true)
    private byte[] expectedPrintModeling; // 예상 출력물 모델링 이미지
}

// 위 엔티티로부터 생성되는 데이터베이스 테이블 스키마는 다음과 같습니다:
//CREATE TABLE print_tb (
//        print_index BIGINT AUTO_INCREMENT PRIMARY KEY,
//        user_index BIGINT NOT NULL,
//        layer INT NOT NULL,
//        actual_print LONGBLOB,
//        is_normal INT NOT NULL,
//        match_rate FLOAT NOT NULL,
//        expected_print_modeling LONGBLOB,
//        FOREIGN KEY (user_index) REFERENCES users(user_index)
//        );