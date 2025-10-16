package com.taskflow.user.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street")
    private String street;

    @Column(name = "addressLine2", length = 100)
    private String addressLine2;

    @Column(name = "houseNumber")
    private Long houseNumber;

    @Column(name = "city", length = 150)
    private String city;

    @Column(name = "county", length = 2)
    private String county;

    @Column(name = "eircode", length = 9)
    private String eirCode;
}