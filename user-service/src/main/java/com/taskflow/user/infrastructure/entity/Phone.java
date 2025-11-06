package com.taskflow.user.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "phone")
@Builder
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "area_code", length = 3)
    private Long areaCode;

    @Column(name = "phone_number", length = 10)
    private Long phoneNumber;

    @Column(name = "user_id")
    private Long userID;


}
