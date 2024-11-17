package com.example.demoProject.dto;

import com.example.demoProject.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private Long userIndex;  // 사용자 고유 식별자
    private String email;    // 이메일
    private String nickname; // 닉네임
    private String passwd;   // (선택적으로 사용) 암호화된 비밀번호
    private String question; // (선택적으로 사용) 보안 질문
    private String answer;   // (선택적으로 사용) 보안 질문의 답변

}
