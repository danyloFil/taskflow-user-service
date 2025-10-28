package com.taskflow.user.business;

import com.taskflow.user.business.converter.UserConverter;
import com.taskflow.user.business.dto.UserDTO;
import com.taskflow.user.infrastructure.entity.User;
import com.taskflow.user.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserDTO salveUser(UserDTO userDTO){
        User user = userConverter.toUser(userDTO);
        return  userConverter.toUserDTO(userRepository.save(user));
    }


}
