package com.example.SpringExercise2.controladores.autorizaciones;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TokenAuthenticationTest {

    private TokenAuthentication tokenAuthentication;

    @BeforeEach
    void setUp() {
        // Configura un objeto TokenAuthentication para las pruebas
        List<String> roles = Arrays.asList("USER", "ADMIN");
        tokenAuthentication = new TokenAuthentication("sampleToken", "userSubject", roles);
    }

    @Test
    void TokenAutenticacionSinProblemas() {
        assertTrue(tokenAuthentication.isAuthenticated());
    }

    @Test
    void TokenVacio() {
        TokenAuthentication auth = new TokenAuthentication("", "subject", List.of("USER"));
        assertFalse(auth.isAuthenticated());
    }

    @Test
    void SubjectVacio() {
        TokenAuthentication auth = new TokenAuthentication("token", "", List.of("USER"));
        assertFalse(auth.isAuthenticated());
    }

    @Test
    void RolesVacio() {
        TokenAuthentication auth = new TokenAuthentication("token", "subject", List.of());
        assertFalse(auth.isAuthenticated());
    }

    @Test
    void ObtenerCredenciales() {
        assertEquals("sampleToken", tokenAuthentication.getCredentials());
    }

    @Test
    void ObtenerPrincipal() {
        assertEquals("userSubject", tokenAuthentication.getPrincipal());
    }

    @Test
    void ObtenerAuthorities() {
        Collection<GrantedAuthority> authorities = tokenAuthentication.getAuthorities();
        assertEquals(2, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Test
    void ObtenerCredenciales2() {
        String tokenBefore = tokenAuthentication.getCredentials().toString();
        tokenAuthentication.getCredentials();
        String tokenAfter = tokenAuthentication.getCredentials().toString();
        assertEquals(tokenBefore, tokenAfter);
    }

    @Test
    void ObtenerPrincipal2() {
        String subjectBefore = tokenAuthentication.getPrincipal().toString();
        tokenAuthentication.getPrincipal();
        String subjectAfter = tokenAuthentication.getPrincipal().toString();
        assertEquals(subjectBefore, subjectAfter);
    }

}
