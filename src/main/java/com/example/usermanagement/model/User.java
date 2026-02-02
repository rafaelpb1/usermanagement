package com.example.usermanagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String username;
    @Column
    private String password;
    @Column
    private Role role;
    @Column
    private Boolean enabled;
}
