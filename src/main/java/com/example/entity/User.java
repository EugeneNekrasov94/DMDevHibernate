package com.example.entity;

import com.example.converter.BirthDateConverter;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
@TypeDef(name = "jsonbType",typeClass = JsonBinaryType.class)
public class User {
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "birth_date")
    //@Convert(converter = BirthDateConverter.class)
    private BirthDate birthDate;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Type(type = "jsonbType")
    private String info;

}
