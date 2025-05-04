package edu.icet.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "USERS")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "VERIFY_CODE")
    private String verifyCode;

    @Column(name = "ACTIVE")
    private Boolean active;

    @Column(name = "VERIFIED")
    private Boolean verified;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "CREATE_DATE_TIME")
    private Date createDateTime = new Date();
}