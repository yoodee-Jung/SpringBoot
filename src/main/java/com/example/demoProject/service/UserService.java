package com.example.demoProject.service;

import com.example.demoProject.dto.UserDto;
import com.example.demoProject.dto.UserRegistrationDto;
import com.example.demoProject.entity.User;
import com.example.demoProject.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(UserRegistrationDto registrationDto) {
        // 이메일 중복 확인
        if (userRepository.existsByEmail(registrationDto.getId())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 닉네임 중복 확인
        if (userRepository.existsByNickname(registrationDto.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        // User Entity 생성
        User user = new User();
        user.setEmail(registrationDto.getId());
        user.setNickname(registrationDto.getNickname());
        user.setPasswd(registrationDto.getPassword()); // 비밀번호 암호화
        user.setQuestion(registrationDto.getSecurityQuestion());
        user.setAnswer(registrationDto.getSecurityAnswer());

        // 데이터베이스에 저장
        userRepository.save(user);
    }


    public void addUser(UserDto userDto) {
        // 중복 이메일 및 닉네임 확인
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email is already in use.");
        }
        if (userRepository.existsByNickname(userDto.getNickname())) {
            throw new IllegalArgumentException("Nickname is already in use.");
        }

        // Entity로 변환 및 저장
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setNickname(userDto.getNickname());
        user.setPasswd(userDto.getPasswd()); // 비밀번호 암호화
        user.setQuestion(userDto.getQuestion());
        user.setAnswer(userDto.getAnswer());

        userRepository.save(user);
    }

    // 모든 유저 조회
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll(); // 모든 유저 조회
        return users.stream()
                .map(this::convertToDto) // Entity -> DTO 변환
                .collect(Collectors.toList());
    }

    // Entity -> DTO 변환
    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setUserIndex(user.getUserIndex());
        dto.setEmail(user.getEmail());
        dto.setNickname(user.getNickname());
        return dto;
    }
}
