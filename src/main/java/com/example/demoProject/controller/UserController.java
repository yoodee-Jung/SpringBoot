package com.example.demoProject.controller;

import com.example.demoProject.dto.UserDto;
import com.example.demoProject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController // REST 컨트롤러 선언
@RequestMapping("/api/users") // API 경로 설정
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 유저 추가 API
    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody UserDto userDto) {
        System.out.println("addUser called");
        try {
            userService.addUser(userDto); // Service 계층 호출
            return ResponseEntity.status(HttpStatus.CREATED).body("User added successfully!");
        } catch (IllegalArgumentException e) {
            System.err.println("Error in addUser: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 모든 유저 조회 API
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        System.out.println("getAllUsers called");
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users); // 200 OK 응답과 함께 반환
    }

//    ############################################################################################################


    private List<String> users = new ArrayList<>();


    // POST 요청: ID(이메일) 중복확인 API
    @PostMapping("/check-id")
    public String checkId(@RequestBody String user) {
//        users.add(user);
//        return "User added: " + user;
        return null;
    }

    // POST 요청: 닉네임 중복확인 API
    @PostMapping("/check-nickname")
    public String checkNickName(@RequestBody String user) {
//        users.add(user);
//        return "User added: " + user;
        return null;
    }

    // POST 요청: 회원가입 API 명세서
    @PostMapping("/register")
    public String registerUser(@RequestBody String user) {
//        users.add(user);
//        return "User added: " + user;
        return null;
    }

    // POST 요청: 보안질문 확인 API
    @PostMapping("/get-security-question")
    public String getSecurityQuestion(@RequestBody String user) {
//        users.add(user);
//        return "User added: " + user;
        return null;
    }

    // POST 요청: 보안질문 답변 확인 API
    @PostMapping("/verify-security-answer")
    public String getVerify(@RequestBody String user) {
//        users.add(user);
//        return "User added: " + user;
        return null;
    }

    // POST 요청: 비밀번호 변경 API
    @PostMapping("/change-password")
    public String changePassword(@RequestBody String user) {
//        users.add(user);
//        return "User added: " + user;
        return null;
    }

    // POST 요청: 로그인 API
    @PostMapping("/login")
    public String login(@RequestBody String user) {
//        users.add(user);
//        return "User added: " + user;
        return null;
    }

    // DELETE 요청: 사용자 삭제
    @DeleteMapping("/{index}")
    public String deleteUser(@PathVariable int index) {
        if (index < 0 || index >= users.size()) {
            return "Invalid index";
        }
        String removedUser = users.remove(index);
        return "User removed: " + removedUser;
    }




//    // POST 요청: 새로운 사용자 추가
//    @PostMapping
//    public String addUser(@RequestBody String user) {
//        users.add(user);
//        return "User added: " + user;
//    }
//
}