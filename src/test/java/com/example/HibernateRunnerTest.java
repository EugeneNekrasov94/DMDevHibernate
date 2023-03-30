package com.example;

import com.example.entity.BirthDate;
import com.example.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.Option;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HibernateRunnerTest {
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
        User user = User.builder()
                .username("ivanov@mail.ru")
                .firstname("ivan")
                .lastname("ivanov")
                .birthDate(new BirthDate(LocalDate.of(2000,1,1)))
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