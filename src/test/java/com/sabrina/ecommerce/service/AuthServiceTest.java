package com.sabrina.ecommerce.service;

import com.sabrina.ecommerce.entity.Role;
import com.sabrina.ecommerce.entity.User;
import com.sabrina.ecommerce.exception.BadRequestException;
import com.sabrina.ecommerce.exception.ResourceNotFoundException;
import com.sabrina.ecommerce.repository.UserRepository;
import com.sabrina.ecommerce.security.JwtService;
import com.sabrina.ecommerce.service.model.request.LoginRequest;
import com.sabrina.ecommerce.service.model.request.RegisterRequest;
import com.sabrina.ecommerce.service.model.response.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    private RegisterRequest registerRequest;

    private LoginRequest loginRequest;

    private User user;


    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setUsername("sabrina");
        registerRequest.setEmail("sabrina@test.com");
        registerRequest.setPassword("1234");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("sabrina@test.com");
        loginRequest.setPassword("1234");

        user = new User();
        user.setId(1L);
        user.setUsername("sabrina");
        user.setEmail("sabrina@test.com");
        user.setPassword("hashedPassword");
        user.setRole(Role.CLIENT);
    }
@Test
    void register_shouldCreateUser_whenEmailNotExists() {

    // Mock
    doReturn(false).when(userRepository).existsByEmail(registerRequest.getEmail());
    doReturn("hashedPassword").when(passwordEncoder).encode(registerRequest.getPassword());
    doReturn(user).when(userRepository).save(any(User.class));

    // WHEN
    User actualUser = authService.register(registerRequest);

    // Then
    assertThat(actualUser).isNotNull();
    assertThat(actualUser.getEmail()).isEqualTo(user.getEmail());
    assertThat(actualUser.getRole()).isEqualTo(user.getRole());
}

    @Test
    void registerAdmin_shouldCreateAdmin() {

        // Mock
        doReturn(false).when(userRepository).existsByEmail(registerRequest.getEmail());
        doReturn("hashedPassword").when(passwordEncoder).encode(registerRequest.getPassword());
        doReturn(user).when(userRepository).save(any(User.class));

        // When
        User actualUser = authService.registerAdmin(registerRequest);

        // Then
        assertThat(actualUser).isNotNull();
        assertThat(actualUser.getEmail()).isEqualTo(registerRequest.getEmail());
        assertThat(actualUser.getRole()).isEqualTo(Role.ADMIN);
    }

    @Test
    void login_shouldReturnAuthResponse_whenCredentialsValid() {
        // GIVEN
        doReturn(Optional.of(user)).when(userRepository).findByEmail("sabrina@test.com");
        doReturn(true).when(passwordEncoder).matches("1234", "hashedPassword");
        doReturn("eyJhbGc...").when(jwtService).generateToken(user);

        // WHEN
        AuthResponse actualUser = authService.login(loginRequest);

        // THEN
        assertThat(actualUser).isNotNull();
        assertThat(actualUser.getToken()).isEqualTo("eyJhbGc...");
        assertThat(actualUser.getEmail()).isEqualTo("sabrina@test.com");
        assertThat(actualUser.getRole()).isEqualTo("CLIENT");
    }

    @Test
    void login_shouldThrowResourceNotFoundException_whenUserNotFound() {
        // GIVEN
        doReturn(Optional.empty()).when(userRepository).findByEmail("unknown@test.com");

        LoginRequest unknownRequest = new LoginRequest();
        unknownRequest.setEmail("unknown@test.com");
        unknownRequest.setPassword("1234");

        // WHEN + THEN
        assertThatThrownBy(() -> authService.login(unknownRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Utilisateur introuvable");
    }

    @Test
    void login_shouldThrowBadRequestException_whenPasswordIncorrect() {
        // GIVEN
        doReturn(Optional.of(user)).when(userRepository).findByEmail("sabrina@test.com");
        doReturn(false).when(passwordEncoder).matches(anyString(), anyString());

        // WHEN + THEN
        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Mot de passe incorrect");
    }
    }



