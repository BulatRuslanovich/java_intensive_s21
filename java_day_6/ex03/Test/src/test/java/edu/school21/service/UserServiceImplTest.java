package edu.school21.service;

import edu.school21.dao.UserDAO;
import edu.school21.dao.impl.UserDAOImpl;
import edu.school21.entity.User;
import edu.school21.excaptions.AlreadyAuthenticatedException;
import edu.school21.excaptions.EntityNotFoundException;
import edu.school21.service.impl.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {
    private EmbeddedDatabase dataSource;
    private UserService usersService;

    private UserService mocService = mock(UserServiceImpl.class);
    private UserDAO mocDao = mock(UserDAOImpl.class);

    @BeforeEach
    public void init() {
        this.dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        try {
            usersService = new UserServiceImpl(dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindByLogin() {
        User user = User.of(0L, "login123", "password123", true);
        mocDao.findByLogin("login123");
        verify(mocDao).findByLogin("login123");

        when(mocDao.findByLogin("login123")).thenReturn(user);
        assertEquals(user, mocDao.findByLogin("login123"));
    }

    @Test
    public void testUpdate() {
        User user = User.of(1L, "adminNew", "admin123New", true);

        ArgumentCaptor<User> valueCaptor = ArgumentCaptor.forClass(User.class);
        doNothing().when(mocDao).update(valueCaptor.capture());
        mocDao.update(user);

        assertEquals(user, valueCaptor.getValue());
    }

    @Test
    public void testAuthenticate() {
        mocService.authenticate("login123", "password123");
        verify(mocService).authenticate("login123", "password123");
    }

    @Test
    public void testPassword_False(){
        mocService.authenticate("login123", "passwordWrong");
        verify(mocService).authenticate("login123", "passwordWrong");

        when(mocService.authenticate("login123", "passwordWrong")).thenReturn(false);
        assertFalse(mocService.authenticate("login123", "passwordWrong"));
    }

    @Test
    public void checkAuthenticateByLoginException() {
        assertThrows(EntityNotFoundException.class, () -> usersService.authenticate("temp", "temp"));
    }

    @Test
    public void checkAlreadyAuthenticatedException(){
        assertThrows(AlreadyAuthenticatedException.class, () -> usersService.authenticate("admin", "admin123"));
    }

    @AfterEach
    public void end() {
        dataSource.shutdown();
    }

}
