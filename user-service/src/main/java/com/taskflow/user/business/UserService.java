package com.taskflow.user.business;

import com.taskflow.user.business.converter.UserConverter;
import com.taskflow.user.business.dto.UserDTO;
import com.taskflow.user.infrastructure.entity.User;
import com.taskflow.user.infrastructure.exception.ConflictException;
import com.taskflow.user.infrastructure.exception.ResourceNotFoundException;
import com.taskflow.user.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

    public UserDTO salveUser(UserDTO userDTO){
        emailExists(userDTO.getEmail());
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userConverter.toUser(userDTO);
        return  userConverter.toUserDTO(userRepository.save(user));
    }

    public void emailExists(String email) {
        try {
            boolean exists = checkIfEmailExists(email);
            if (exists) {
                throw new ConflictException("Email already registered: " + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email already registered", e.getCause());
        }
    }

    public boolean checkIfEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email not found: " + email));
    }

    public void deleteUserByEmail(String email) {
        userRepository.deleteByEmail(email);
    }



}
