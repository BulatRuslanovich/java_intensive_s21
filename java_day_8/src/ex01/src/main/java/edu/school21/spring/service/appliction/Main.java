package edu.school21.spring.service.appliction;

import edu.school21.spring.service.models.User;
import edu.school21.spring.service.repositories.UserRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        UserRepository usersRepository = context.getBean("userRepositoryJdbc", UserRepository.class);

        usersRepository.save(new User("lol@gmail.com"));
        usersRepository.update(new User(1L, "popo"));
        System.out.println(usersRepository.findByEmail("popo"));
        System.out.println(usersRepository.findById(3L));

        System.out.println(usersRepository.findAll());
        usersRepository = context.getBean("userRepositoryJdbcTemplate", UserRepository.class);
        usersRepository.save(new User("lol@gmail.com"));
        usersRepository.update(new User(1L, "popo"));
        System.out.println(usersRepository.findByEmail("popo"));

        System.out.println(usersRepository.findById(3L));

        System.out.println(usersRepository.findAll());
    }
}
