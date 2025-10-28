package com.taskflow.user.business.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<AddressDTO> addressDTOS;
    private List<PhoneDTO> phones;




}
