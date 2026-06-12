package com.utn.corralon.features.auth;

import com.utn.corralon.features.user.dto.UserRequestDTO;
import com.utn.corralon.features.user.dto.UserResponseDTO;
import com.utn.corralon.features.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final JwtService jwtService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(
            @RequestBody AuthRequest authRequest){
        UserDetails user = authService.authenticate(authRequest);
        System.out.println(user);
        String token = jwtService.generateToken(user);
        System.out.println(token);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(
            @RequestBody UserRequestDTO userRequestDTO){
        return new ResponseEntity<>(userService.create(userRequestDTO),
                HttpStatus.CREATED);
    }

}
