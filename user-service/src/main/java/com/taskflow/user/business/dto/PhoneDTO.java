package com.taskflow.user.business.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneDTO {

    private Long phoneNumber;
    private Long areaCode;
}
