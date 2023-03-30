package com.example.entity;

import com.example.converter.BirthDateConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "birth_date")
    @Convert(converter = BirthDateConverter.class)
    private BirthDate birthDate;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;


}
