package com.example.SpringExercise2.controladores.autorizaciones;

import com.example.SpringExercise2.data.usuarios.UserEntity;
import com.example.SpringExercise2.data.usuarios.UserService;
import com.example.SpringExercise2.excepciones.InvalidCredentialsException;
import com.example.SpringExercise2.seguridadJwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/authentication")
public class AuthController {
    private UserService userService;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
        Optional<UserEntity> optionalUser = userService.buscarPorCorreo(loginDto.getUsuario());
        if (optionalUser.isEmpty() || !BCrypt.checkpw(loginDto.getContraseña(), optionalUser.get().getContraseña())) {
            throw new InvalidCredentialsException("Nombre de usuario o contraseña inválida");
        }

        UserEntity user = optionalUser.get();
        TokenDto tokenDto = jwtUtil.generateToken(user.getEmail(), user.getRoles());
        return ResponseEntity.ok(tokenDto);
    }
}
