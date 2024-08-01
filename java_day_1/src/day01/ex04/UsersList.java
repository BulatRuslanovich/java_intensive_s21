package day01.ex04;


public interface UsersList {
    void add(User user);
    User getUserById(Long id);
    User getUserByIndex(int index);
    int getUserCount();
}
