package com.example;

import com.example.entity.BirthDate;
import com.example.entity.Role;
import com.example.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class HibernateRunner {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            System.out.println("OK");
            User user = User.builder()
                    .username("ivanov@mail.ru")
                    .firstname("ivan")
                    .lastname("ivanov")
                    .birthDate(new BirthDate(LocalDate.of(2000,1,1)))
                    .role(Role.ADMIN)
                    .build();
            session.save(user);
            session.getTransaction().commit();
        }

    }
}
