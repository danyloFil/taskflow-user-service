package com.taskflow.user.controller;

import com.taskflow.user.business.UserService;
import com.taskflow.user.business.dto.AddressDTO;
import com.taskflow.user.business.dto.PhoneDTO;
import com.taskflow.user.business.dto.UserDTO;
import com.taskflow.user.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UserDTO> salveUserDTO(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.createUser(userDTO));

    }

    @PostMapping("/login")
    public String login(@RequestBody UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getEmail(),
                        userDTO.getPassword())
        );
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<UserDTO> getUserByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUserByEmail(@PathVariable String email) {
        userService.removeUserByEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUserProfile(@RequestBody UserDTO userDTO,
                                                     @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.updateUserProfile(token, userDTO));
    }

    @PutMapping("/address")
    public ResponseEntity<AddressDTO> updateAddress(@RequestBody AddressDTO addressDTO,
                                                    @RequestParam("id") Long id){

        return  ResponseEntity.ok(userService.updateAddress(id, addressDTO));
    }

    @PutMapping("/phoneNumber")
    public ResponseEntity<PhoneDTO> updatePhoneNumber(@RequestBody PhoneDTO phoneDTO,
                                                      @RequestParam("id") Long id){

        return  ResponseEntity.ok(userService.updatePhoneNumber(id, phoneDTO));
    }

    @PostMapping("/createAddress")
    public ResponseEntity<AddressDTO> createAddress(@RequestBody AddressDTO addressDTO,
                                                    @RequestHeader("Authorization") String token){
           return ResponseEntity.ok(userService.createAddress(token, addressDTO));

    }

    @PostMapping("createPhoneNumber")
    public ResponseEntity<PhoneDTO> createPhoneNumber(@RequestBody PhoneDTO phoneDTO,
                                                      @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.createPhoneNumber(token, phoneDTO));
        
    }

}
