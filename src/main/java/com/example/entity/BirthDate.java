package com.example.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public record BirthDate(LocalDate birthDate) {
        public long getAge() {
            return ChronoUnit.YEARS.between(birthDate,LocalDateTime.now());
        }
}
