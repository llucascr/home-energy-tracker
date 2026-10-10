package com.llucascr.user_service.service;

import com.llucascr.user_service.dto.UserDto;
import com.llucascr.user_service.entity.User;
import com.llucascr.user_service.exception.UserAlreadyExistsException;
import com.llucascr.user_service.exception.UserNotFoundException;
import com.llucascr.user_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldNotCreateUserWhenEmailAlreadyExists() {
        UserDto input = createUserDto("user@example.com");
        User existingUser = User.builder()
                .id(1L)
                .email(input.email())
                .build();

        when(userRepository.findByEmail(input.email())).thenReturn(Optional.of(existingUser));

        assertThatThrownBy(() -> userService.createUser(input))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage("User already exists with email: user@example.com");
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(99L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found with id: 99");
    }

    private UserDto createUserDto(String email) {
        return new UserDto(
                null,
                "Test",
                "User",
                email,
                "Test address",
                true,
                100.0
        );
    }
}
