package com.example.demoProject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "printer_tb")
@Getter
@Setter
@NoArgsConstructor
public class Printer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 자동 생성
    @Column(name = "printer_index")
    private Long printerIndex; // 프린터 인덱스 (PK)

    @Column(name = "printer_nickname", nullable = false, length = 100)
    private String printerNickname; // 프린터 닉네임

    @ManyToOne // 다대일 관계 (여러 프린터가 하나의 유저를 참조)
    @JoinColumn(name = "user_index", nullable = false) // 외래 키 설정
    private User user; // 유저 인덱스 (FK)

    @Column(name = "bad_size_rows", nullable = false)
    private Integer badSizeRows; // 불량 크기 행 수

    @Column(name = "bad_size_columns", nullable = false)
    private Integer badSizeColumns; // 불량 크기 열 수

    @Lob // 대용량 데이터(이미지 파일) 처리
    @Column(name = "calibration_image_top", nullable = true)
    private byte[] calibrationImageTop; // 캘리브레이션 상단 이미지

    @Lob // 대용량 데이터(이미지 파일) 처리
    @Column(name = "calibration_image_bottom", nullable = true)
    private byte[] calibrationImageBottom; // 캘리브레이션 하단 이미지

    @Lob // 대용량 데이터(이미지 파일) 처리
    @Column(name = "calibration_image_middle", nullable = true)
    private byte[] calibrationImageMiddle; // 캘리브레이션 중간 이미지
}

//위 엔티티를 기반으로 생성될 데이터베이스 테이블 스키마는 다음과 같습니다:
//CREATE TABLE printer_tb (
//        printer_index BIGINT AUTO_INCREMENT PRIMARY KEY,
//        printer_nickname VARCHAR(100) NOT NULL,
//        user_index BIGINT NOT NULL,
//        bad_size_rows INT NOT NULL,
//        bad_size_columns INT NOT NULL,
//        calibration_image_top LONGBLOB,
//        calibration_image_bottom LONGBLOB,
//        calibration_image_middle LONGBLOB,
//        FOREIGN KEY (user_index) REFERENCES users(user_index)
//);