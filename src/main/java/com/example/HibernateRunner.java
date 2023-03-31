package com.example;

import com.example.entity.User;
import com.example.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateRunner {
    public static final Logger logger = LoggerFactory.getLogger(HibernateRunner.class);
    public static void main(String[] args) {
        User user = User.builder()
                .username("ivanov@mail.ru")
                .firstname("ivan")
                .lastname("ivanov")
                .info("""
                        {
                        "city":"Ukhta"
                        }
                        """)
                .build();
        logger.info("User entity is transient state, object: {}" ,user);
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();
            try(session1) {
                Transaction transaction = session1.beginTransaction();
                logger.trace("transaction is created, {}" , transaction);
                session1.saveOrUpdate(user);
                logger.trace("User {} is in persistent state: session is {}",user,session1);
                session1.getTransaction().commit();
            }
            logger.warn("Session is closed {}",session1);
            try(Session session2 = sessionFactory.openSession()) {
                session2.beginTransaction();
                user.setFirstname("Carl");
                session2.merge(user);
                session2.getTransaction().commit();
            }
        } catch (Exception e) {
            logger.error("Exception occured",e);
            throw e;
        }

    }
}
