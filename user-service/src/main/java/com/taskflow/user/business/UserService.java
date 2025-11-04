package com.taskflow.user.business;

import com.taskflow.user.business.converter.UserConverter;
import com.taskflow.user.business.dto.UserDTO;
import com.taskflow.user.infrastructure.entity.User;
import com.taskflow.user.infrastructure.exception.ConflictException;
import com.taskflow.user.infrastructure.exception.ResourceNotFoundException;
import com.taskflow.user.infrastructure.repository.UserRepository;
import com.taskflow.user.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;

    public UserDTO createUser(UserDTO userDTO){
        validateEmailNotExists(userDTO.getEmail());
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userConverter.convertToEntity(userDTO);
        return  userConverter.convertToDTO(userRepository.save(user));
    }

    public void validateEmailNotExists(String email) {
        try {
            boolean exists = isEmailRegistered(email);
            if (exists) {
                throw new ConflictException("Email already registered: " + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email already registered", e.getCause());
        }
    }

    public boolean isEmailRegistered(String email) {
        return userRepository.existsByEmail(email);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email not found: " + email));
    }

    public void removeUserByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    public UserDTO updateUserProfile(String token, UserDTO userDTO) {
        // Retrieve the user's email from the token (remove the need for email in the request)
        String email = jwtUtil.extractTokenEmail(token.substring(7));

        // Encrypt the password
        userDTO.setPassword(userDTO.getPassword() != null ? passwordEncoder.encode(userDTO.getPassword()) : null);

        // Retrieve user data from the database
        User userEntity = userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email not found"));

        // Merge the data received in the DTO with the data from the database
        User user = userConverter.updateEntityFromDTO(userDTO, userEntity);

        return userConverter.convertToDTO(userRepository.save(user));
    }


}
