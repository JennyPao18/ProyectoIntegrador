package com.example.SpringExercise2.controladores;

import com.example.SpringExercise2.data.usuarios.RoleEnum;
import com.example.SpringExercise2.data.usuarios.UserEntity;
import com.example.SpringExercise2.data.usuarios.UserService;
import com.example.SpringExercise2.excepciones.UserWithEmailAlreadyRegisteredException;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.example.SpringExercise2.utils.Constants.ADMIN_ROLE;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        loadSampleUsers();
    }

    public void loadSampleUsers() {
        if (passwordEncoder != null) {
            UserEntity userEntity = new UserEntity("Paola", "paolita@email.com", passwordEncoder.encode("123"));
            userService.crearUsuario(userEntity);
            UserEntity adminUserEntity = new UserEntity("Angel", "angelito@email.com", passwordEncoder.encode("321"));
            adminUserEntity.addRole(RoleEnum.ADMIN);
            userService.crearUsuario(adminUserEntity);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable String id) {
        Optional<UserEntity> user = userService.buscarPorId(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserDto userDto) {
        if (userService.buscarPorCorreo(userDto.getEmail()).isPresent()) {
            throw new UserWithEmailAlreadyRegisteredException("Ese correo electrónico ya está en uso");
        }

        UserEntity userEntity = new UserEntity(userDto.getNombre(), userDto.getEmail(), passwordEncoder.encode(userDto.getContraseña()));
        UserEntity savedUser = userService.crearUsuario(userEntity);

        return ResponseEntity.ok(savedUser);
    }

    @RolesAllowed(ADMIN_ROLE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable String id) {
        Optional<UserEntity> user = userService.buscarPorId(id);
        if (user.isPresent()) {
            userService.eliminarUsuario(user.get());
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }
}

