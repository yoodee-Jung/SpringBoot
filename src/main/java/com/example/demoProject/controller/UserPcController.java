package com.example.demoProject.controller;

import com.example.demoProject.dto.UserDto;
import com.example.demoProject.repository.UserRepository;
import com.example.demoProject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // REST 컨트롤러 선언
@RequestMapping("/api/pc_users") // API 경로 설정
public class UserPcController {

    private final UserService userService;
    private final UserRepository userRepository;


    public UserPcController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    // 로그인 API
    @PostMapping("/loginProcess")
    public ResponseEntity<String> loginProcess(@RequestBody UserDto userDto) {
        return null;
    }

    // gcode 업로드 API
    @PostMapping("/UploadFile")
    public ResponseEntity<String> UploadFile(@RequestBody UserDto userDto) {
        return null;
    }

    // 프린터 캘리브레이션 명세서 - top
    @PostMapping("/printer/img/top")
    public ResponseEntity<String> printerImgTop(@RequestBody UserDto userDto) {
        return null;
    }

    // 프린터 캘리브레이션 명세서 - bottom
    @PostMapping("/printer/img/bottom")
    public ResponseEntity<String> printerImgBottom(@RequestBody UserDto userDto) {
        return null;
    }

    // 프린터 캘리브레이션 명세서 - middle
    @PostMapping("/printer/img/middle")
    public ResponseEntity<String> printerImgMiddle(@RequestBody UserDto userDto) {
        return null;
    }
}
