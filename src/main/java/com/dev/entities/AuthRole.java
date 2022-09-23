package com.dev.entities;

import com.dev.constants.EAuthRole;

import javax.persistence.*;

@Entity
@Table(name = "AuthRole")
public class AuthRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EAuthRole name;

}
