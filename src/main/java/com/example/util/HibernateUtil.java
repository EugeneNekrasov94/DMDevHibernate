package com.example.util;

import com.example.converter.BirthDateConverter;
import com.example.entity.User;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {
    public static SessionFactory buildSessionFactory() {

        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.addAttributeConverter(new BirthDateConverter());
        configuration.registerTypeOverride(new JsonBinaryType());

        configuration.configure();
        return configuration.buildSessionFactory();
    }
}
