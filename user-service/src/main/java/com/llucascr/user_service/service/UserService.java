package com.llucascr.user_service.service;

import com.llucascr.user_service.dto.UserDto;
import com.llucascr.user_service.entity.User;
import com.llucascr.user_service.exception.UserAlreadyExistsException;
import com.llucascr.user_service.exception.UserNotFoundException;
import com.llucascr.user_service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto createUser(UserDto input) {
        ensureEmailIsAvailable(input.email(), null);

        final User createdUser = User.builder()
                .name(input.name())
                .surname(input.surname())
                .email(input.email())
                .address(input.address())
                .alerting(input.alerting())
                .energyAlertingThreshold(input.energyAlertingThreshold())
                .build();

        final User saved = userRepository.save(createdUser);
        return toDto(saved);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public UserDto getUserById(Long id) {
        return toDto(findUserById(id));
    }

    public UserDto updateUser(Long id, UserDto input) {
        User user = findUserById(id);
        ensureEmailIsAvailable(input.email(), id);

        user.setName(input.name());
        user.setSurname(input.surname());
        user.setEmail(input.email());
        user.setAddress(input.address());
        user.setAlerting(input.alerting());
        user.setEnergyAlertingThreshold(input.energyAlertingThreshold());

        return toDto(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        User user = findUserById(id);
        userRepository.delete(user);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    private void ensureEmailIsAvailable(String email, Long currentUserId) {
        userRepository.findByEmail(email)
                .filter(user -> !user.getId().equals(currentUserId))
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("Invalid Credentials");
                });
    }

    private UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .address(user.getAddress())
                .alerting(user.isAlerting())
                .energyAlertingThreshold(user.getEnergyAlertingThreshold())
                .build();
    }

}
