package com.example;

import com.example.entity.BirthDate;
import com.example.entity.Company;
import com.example.entity.PersonalInfo;
import com.example.entity.User;
import com.example.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

@Slf4j
public class HibernateRunner {
    public static void main(String[] args) {
        Company company = Company.builder().name("Google").build();
        User user = User.builder()
                .username("ivanov@mail.ru")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Elena")
                        .lastname("Prekrasnaya")
                        .birthDate(new BirthDate(LocalDate.of(2000,1,1))).build())

                .info("""
                        {
                        "city":"Ukhta"
                        }
                        """)
                .company(company)
                .build();
        log.info("User entity is transient state, object: {}" ,user);
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();
            try(session1) {
                Transaction transaction = session1.beginTransaction();
                session1.get(User.class,1L);
                /*session1.save(company);
                session1.save(user);*/
                session1.getTransaction().commit();
            }

        }

    }
}
