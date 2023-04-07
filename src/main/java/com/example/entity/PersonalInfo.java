package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PersonalInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 8648745035698632856L;
    private String firstname;
    private String lastname;
    private BirthDate birthDate;
}
