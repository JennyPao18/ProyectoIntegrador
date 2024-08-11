package com.example.SpringExercise2.data.usuarios;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserEntityTest {
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity("John Doe", "john.doe@example.com", "password123");
    }

    @Test
    void AñadirRol() {
        userEntity.addRole(RoleEnum.ADMIN);
        assertEquals(2, userEntity.getRoles().size());
        assertTrue(userEntity.getRoles().contains(RoleEnum.ADMIN));
    }

    @Test
    void AñadirRolExistente() {
        userEntity.addRole(RoleEnum.USER);
        assertEquals(1, userEntity.getRoles().size()); // Should still only have one role
    }
}
