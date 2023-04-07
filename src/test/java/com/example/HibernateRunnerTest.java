package com.example;

import com.example.entity.Chat;
import com.example.entity.PersonalInfo;
import com.example.entity.User;
import com.example.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

class HibernateRunnerTest {

    @Test
    void testManyToMany() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()){
            session.beginTransaction();
            var user = session.get(User.class,1L);
            var chat = Chat.builder().name("kata").build();
            user.addChat(chat);
            session.save(chat);
            session.getTransaction().commit();
        }
    }
    @Test
    void testReflection() throws SQLException, IllegalAccessException {
        String sql = """
                insert
                into
                %s
                (%s)
                values
                (%s)
                """;
        PersonalInfo personalInfo = PersonalInfo.builder().firstname("Vanya").lastname("Shatalov").build();
        User user = User.builder()
                .username("ivanov@mail.ru")
                .personalInfo(personalInfo)
                .build();
        String tableName = Optional.ofNullable(user.getClass().getAnnotation(Table.class)).map(table -> table.schema() + table.name())
                .orElse(user.getClass().getName());
        Field[] declaredFields = user.getClass().getDeclaredFields();
        String field = Arrays.stream(declaredFields)
                .map(fieldName -> Optional.ofNullable(fieldName.getAnnotation(Column.class))
                        .map(Column::name)
                        .orElse(fieldName.getName())).collect(Collectors.joining(" ,"));
        String columnValues = Arrays.stream(declaredFields).map(field1 -> "?").collect(Collectors.joining(", "));
        System.out.println(sql.formatted(tableName,field,columnValues));

        Connection connection = null;
        PreparedStatement statement = connection.prepareStatement(sql.formatted(tableName,field,columnValues));
        for (Field field1: declaredFields) {
            field1.setAccessible(true);
            statement.setObject(1,field1.get(user));
        }

    }

}