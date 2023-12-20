package com.user.mgnt.graphql.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "users")
@Entity
public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    @Column(name = "email", unique = true)
    private String email;
    private String password;
    private String confirmPassword;
    private String mobileNumber;
    private String address;
    private String userId;
}


