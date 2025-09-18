package com.raja.controller;

import com.raja.dto.UserDTO;
import com.raja.entity.User;
import com.raja.mapper.UserMapper;
import com.raja.repository.UserRepository;
import com.raja.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper mapper;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        User loginRequest = mapper.toUser(userDTO);
        Optional<User> userOpt = userRepository.findByUsername(loginRequest.getUsername());

        if (userOpt.isEmpty() || !passwordEncoder.matches(loginRequest.getPassword(), userOpt.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        }

        User user = userOpt.get();
        String accessToken = JwtUtil.generateToken(user.getUsername(), user.getRole());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        return new ResponseEntity<>(mapper.toUserDTO(user), headers, HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        User user = mapper.toUser(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        User save = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toUserDTO(save));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestParam String refreshToken) {
        String newAccessToken = JwtUtil.generateToken("raja", "USER");
        return ResponseEntity.ok("Bearer " + newAccessToken);
    }
}
