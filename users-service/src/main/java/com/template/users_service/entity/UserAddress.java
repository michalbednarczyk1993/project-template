package com.template.users_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "user_addresses")
public class UserAddress {
    @Id
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String street;
    private String city;
    @Column(name = "zip_code")
    private String zipCode;
}