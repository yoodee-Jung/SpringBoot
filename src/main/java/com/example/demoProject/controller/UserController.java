package com.example.demoProject.controller;

import com.example.demoProject.dto.UserDto;
import com.example.demoProject.dto.UserRegistrationDto;
import com.example.demoProject.entity.User;
import com.example.demoProject.repository.UserRepository;
import com.example.demoProject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController // REST 컨트롤러 선언
@RequestMapping("/api/users") // API 경로 설정
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    // 생성자 주입
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
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

    // POST 요청: ID(이메일) 중복 확인 API
    @PostMapping("/check-id")
    public Map<String, Object> checkId(@RequestBody String email) {
        // 결과를 저장할 Map
        Map<String, Object> response = new HashMap<>();

        // 이메일 중복 확인
        boolean exists = userRepository.existsByEmail(email.trim());

        // 응답 작성
        if (exists) {
            response.put("isAvailable", false);
            response.put("message", "이미 사용 중인 ID입니다.");
        } else {
            response.put("isAvailable", true);
            response.put("message", "사용 가능한 ID입니다.");
        }

        return response;
    }


    // POST 요청: 닉네임 중복확인 API
    @PostMapping("/check-nickname")
    public Map<String, Object> checkNickName(@RequestBody String nickname) {
        // 결과를 저장할 Map
        Map<String, Object> response = new HashMap<>();

        // 닉네임 중복 확인
        boolean exists = userRepository.existsByNickname(nickname.trim());

        // 응답 작성
        if (exists) {
            response.put("isAvailable", false);
            response.put("message", "이미 사용 중인 닉네임입니다.");
        } else {
            response.put("isAvailable", true);
            response.put("message", "사용 가능한 닉네임입니다.");
        }
        return response;
    }


    // POST 요청: 회원가입 API 명세서
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserRegistrationDto registrationDto) {
        Map<String, String> response = new HashMap<>();

        try {
            userService.registerUser(registrationDto); // 회원가입 처리
            response.put("message", "회원가입이 완료되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.CREATED); // 201 Created
        } catch (IllegalArgumentException e) {
            response.put("message", e.getMessage()); // 입력값 검증 실패
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 400 Bad Request
        } catch (Exception e) {
            response.put("message", "회원가입에 실패했습니다. 다시 시도해주세요."); // 서버 에러
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }

    // POST 요청: 보안질문 확인 API
    @PostMapping("/get-security-question")
    public ResponseEntity<Map<String, String>> getSecurityQuestion(@RequestBody Map<String, String> request) {
        String email = request.get("id"); // 요청에서 ID(email) 추출
        Map<String, String> response = new HashMap<>();

        // 데이터베이스에서 해당 이메일의 User 찾기
        User user = userRepository.findByEmail(email);

        if (user != null) {
            // 성공: 보안 질문 반환
            response.put("securityQuestion", user.getQuestion());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // 실패: ID가 존재하지 않음
            response.put("message", "해당 ID의 보안질문을 찾을 수 없습니다.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    // POST 요청: 보안질문 답변 확인 API
    @PostMapping("/verify-security-answer")
    public ResponseEntity<Map<String, Object>> verifySecurityAnswer(@RequestBody Map<String, String> request) {
        String email = request.get("id");
        String securityAnswer = request.get("securityAnswer");

        Map<String, Object> response = new HashMap<>();

        // 데이터베이스에서 해당 이메일의 사용자 찾기
        User user = userRepository.findByEmail(email);

        if (user != null) {
            // 보안 질문 답변 검증
            if (user.getAnswer().equalsIgnoreCase(securityAnswer.trim())) {
                response.put("isCorrect", true);
                response.put("message", "보안 질문 답변이 일치합니다.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("isCorrect", false);
                response.put("message", "보안 질문 답변이 일치하지 않습니다.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } else {
            response.put("isCorrect", false);
            response.put("message", "해당 ID를 찾을 수 없습니다.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }



    // POST 요청: 비밀번호 변경 API
    @PostMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(@RequestBody Map<String, String> request) {
        String email = request.get("id");
        String newPassword = request.get("newPassword");

        Map<String, String> response = new HashMap<>();

        try {
            // 이메일로 사용자 찾기
            User user = userRepository.findByEmail(email);

            if (user != null) {
                // 새 비밀번호 암호화 후 저장
                user.setPasswd(newPassword);
                userRepository.save(user);

                // 성공 응답
                response.put("message", "비밀번호가 성공적으로 변경되었습니다.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                // 사용자 없음
                response.put("message", "해당 ID를 찾을 수 없습니다.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            // 기타 실패
            response.put("message", "비밀번호 변경에 실패했습니다. 다시 시도해주세요.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        String email = request.get("id");
        String password = request.get("password");

        Map<String, Object> response = new HashMap<>();

        // 사용자 검증
        User user = userRepository.findByEmail(email);
        if (user != null && password.equals(user.getPasswd())) {
            // 성공: JWT 토큰 또는 index 반환
            response.put("message", "로그인 성공");
            response.put("index", generateJwt(user)); // JWT 생성
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // 실패: 잘못된 ID 또는 비밀번호
            response.put("message", "ID 또는 비밀번호가 잘못되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    // JWT 토큰 생성 메서드 (간단한 예제)
    private String generateJwt(User user) {
        // 실제 구현 시, JWT 라이브러리를 사용하여 토큰 생성
        return "user_" + user.getUserIndex(); // 예: 유저의 ID 기반 토큰
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