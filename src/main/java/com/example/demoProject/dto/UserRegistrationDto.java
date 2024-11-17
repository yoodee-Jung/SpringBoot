package com.example.demoProject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDto {
    private String id; // 이메일
    private String nickname;
    private String password;
    private String securityQuestion;
    private String securityAnswer;
}