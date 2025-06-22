package uz.dev.hmsproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.dev.hmsproject.dto.request.LoginDTO;
import uz.dev.hmsproject.dto.request.UserReqDTO;
import uz.dev.hmsproject.dto.response.TokenDTO;
import uz.dev.hmsproject.service.security.AuthService;
import uz.dev.hmsproject.service.template.UserService;

/**
 * Created by: asrorbek
 * DateTime: 6/21/25 13:34
 **/

@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    @PostMapping("/login")
    public TokenDTO login(@RequestBody @Valid LoginDTO loginDTO) {

        return authService.getToken(loginDTO);

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserReqDTO dto) {

        userService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body("Created User successfully");
    }
}
