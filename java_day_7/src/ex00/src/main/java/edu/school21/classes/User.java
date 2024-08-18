package edu.school21.classes;

import java.time.LocalDate;
import java.util.StringJoiner;

public class User {
    private final String firstName;
    private final String lastName;
    private final int age;

    public User() {
        this.firstName = "NoName";
        this.lastName = "NoSurname";
        this.age = 0;
    }

    public User(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public int ageAfter(int year) {
        return this.age + year - LocalDate.now().getYear();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("age=" + age)
                .toString();
    }
}
