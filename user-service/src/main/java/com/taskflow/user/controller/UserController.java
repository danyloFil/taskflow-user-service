package com.taskflow.user.controller;

import com.taskflow.user.business.UserService;
import com.taskflow.user.business.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("*/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> salveUserDTO(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.salveUser(userDTO));

    }

}
