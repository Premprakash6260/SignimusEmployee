package com.signimus.employee2.Entity;

import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Table(name = "users")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "First name is required")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull(message = "Last name is required")
    @Column(name = "last_name", nullable = false)
    private String lastName;

//    email
    @NotNull(message = "Email is required")
    @Column(name = "email", nullable = false, unique = true)
    @Email
    private String email;

    @NotNull(message = "Password is required")
    @Column(name = "password", nullable = false)
    private String password;

//    role for each user
    @NotNull(message = "Role is required")
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles role;

//    create a field age
    @NotNull(message = "Age is required")
    @Column(name = "age", nullable = false)
    private Integer age;

    //    add a field createAt, to store created date and time automatically
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
