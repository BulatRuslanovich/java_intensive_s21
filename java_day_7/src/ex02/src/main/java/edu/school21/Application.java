package edu.school21;

import edu.school21.entity.User;
import edu.school21.service.UserService;

public class Application {
    public static void main(String[] args) {
        UserService userService = new UserService();
        User user = new User("Ryan", "Gosling", 43);
        userService.save(user);
        user.setLastName("Reynolds");
        user.setAge(47);
        userService.update(user);

        System.out.println(user);
        System.out.println(userService.getUserById(3L));
    }
}

