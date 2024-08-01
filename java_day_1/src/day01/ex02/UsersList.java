package day01.ex02;

public interface UsersList {
    void addUser(User user);
    User getUserById(Long id);
    User getUserByIndex(int index);
    int getUserCount();
}
