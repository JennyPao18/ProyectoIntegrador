package com.example.SpringExercise2.controladores;

import com.example.SpringExercise2.modelos.User;
import com.example.SpringExercise2.servicios.UserService;
import com.example.SpringExercise2.servicios.UserServiceMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
//Ruta en común para todos los métodos
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceMethods userServiceMethods;

    //Método para crear usuarios con Post
    @PostMapping
    public ResponseEntity<User> crearUsuario(@RequestBody User user){
        User usuarioCreado = userServiceMethods.crearUsuario(user);
        return new ResponseEntity<>(usuarioCreado, HttpStatus.CREATED);
    }

    //Método para actualizar usuarios con Put
    @PutMapping("/{id}")
    public ResponseEntity<User> actualizarUsuarios(@PathVariable long id, @RequestBody User user){
        User usuarioActualizado = userServiceMethods.actualizarUsuario(id, user);
        return usuarioActualizado != null ? ResponseEntity.ok(usuarioActualizado) : ResponseEntity.notFound().build();
    }

    //Método para eliminar usuarios con Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
        userServiceMethods.eliminarUsuario(id);
        String message = "Usuario eliminado correctamente";
        return ResponseEntity.ok(message);
    }

    //Método para buscar usuarios por id con Get
    @GetMapping("/{id}")
    public ResponseEntity<User> buscarUsuarioPorId(@PathVariable Long id) {
        User user = userServiceMethods.buscarUsuarioPorId(id);
        if (user != null){
            return ResponseEntity.ok(user);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    //Método para mostrar a todos los usuarios con Get
    @GetMapping
    public ResponseEntity<List<User>> mostrarUsuarios() {
        List<User> usuarios = userServiceMethods.mostrarUsuarios();
        return ResponseEntity.ok(usuarios);
    }
}
