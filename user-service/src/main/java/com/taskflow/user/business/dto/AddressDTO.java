package com.taskflow.user.business.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {

    private Long id;
    private String street;
    private String addressLine2;
    private Long houseNumber;
    private String city;
    private String county;
    private String eirCode;


}
