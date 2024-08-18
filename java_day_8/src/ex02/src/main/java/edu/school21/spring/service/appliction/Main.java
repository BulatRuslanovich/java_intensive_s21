package edu.school21.spring.service.appliction;


import edu.school21.spring.service.repositories.UserRepository;
import edu.school21.spring.service.services.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("edu.school21.spring.service");
        UserService usersService = context.getBean("userServiceImpl", UserService.class);
        UserRepository usersJdbcTemplate = context.getBean("jdbcTemplateRepository", UserRepository.class);

        for (int i = 0; i < 10; i++) {
            System.out.println("User " + (i + 1) + " is added.");
            StringBuilder str = new StringBuilder("user" + (i + 1) + "@school21.ru");
            System.out.println("Password: " + usersService.signUp(str.toString()));
        }

        System.out.println("\n------------------------------------");
        System.out.println("Find all: \n" + usersJdbcTemplate.findAll());
        System.out.println("\n------------------------------------");

        System.out.println("Find by Id: \n" + usersJdbcTemplate.findById(1L));
        System.out.println("\n------------------------------------");

        System.out.println("Find by non-exists email: \n" + usersJdbcTemplate.findByEmail("01234@school21.ru"));
        System.out.println("\n------------------------------------");

        System.out.println("Find by real email: \n" + usersJdbcTemplate.findByEmail("user1@school21.ru"));
        System.out.println("\n------------------------------------");
    }
}
