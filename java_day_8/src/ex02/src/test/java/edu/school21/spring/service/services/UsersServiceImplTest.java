package edu.school21.spring.service.services;

import edu.school21.spring.service.config.TestApplicationConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UsersServiceImplTest {
    private static DataSource dataSource;
    private static UserService usersServiceJdbcTemplate;

    @BeforeAll
    static void before() {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        dataSource = context.getBean("dataSource", DataSource.class);
        usersServiceJdbcTemplate = context.getBean("userServiceImpl", UserService.class);
    }

    @BeforeEach
    public void init() {
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            st.executeUpdate("DROP TABLE IF EXISTS day08_2.app_user;");
            st.executeUpdate("CREATE TABLE day08_2.app_user(user_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,\n email VARCHAR(255), password VARCHAR(255));");
            st.executeUpdate("INSERT INTO day08_2.app_user(email, password) VALUES('user3@school21.ru', '123');");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @AfterAll
    public static void clean() {
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            st.executeUpdate("DROP TABLE IF EXISTS day08_2.app_user;");
            st.executeUpdate("CREATE TABLE day08_2.app_user(user_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,\n email VARCHAR(255), password VARCHAR(255));");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"user1@school21.ru", "user2@school21.ru", "user3@school21.ru"})
    void isSignedUpTemplate(String email) {
        assertNotNull(usersServiceJdbcTemplate.signUp(email));
    }
}
