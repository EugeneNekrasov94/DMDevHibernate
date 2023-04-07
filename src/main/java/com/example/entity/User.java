package com.example.entity;

import com.example.converter.BirthDateConverter;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
@TypeDef(name = "jsonbType",typeClass = JsonBinaryType.class)
@EqualsAndHashCode(of = "username")
@ToString(exclude = {"company","profile","chats"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username",unique = true)
    private String username;
    @AttributeOverride(name = "birthDate",column = @Column(name = "birth_date"))
    private PersonalInfo personalInfo;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Type(type = "jsonbType")
    private String info;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private Company company;
    @Builder.Default
    @ManyToMany
    @JoinTable(name = "users_chat",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id"))
    private Set<Chat> chats = new HashSet<>();

    public void addChat(Chat chat) {
        chats.add(chat);
        chat.getUserList().add(this);
    }


}
