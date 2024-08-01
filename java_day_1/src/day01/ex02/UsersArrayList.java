package day01.ex02;

public class UsersArrayList implements UsersList {
    private int count = 0;
    private int maxCount = 10;
    private User[] users = new User[maxCount];

    @Override
    public void addUser(User user) {
        if (count == maxCount) {
            maxCount *= 2;
            User[] newUsers = new User[maxCount];
            System.arraycopy(users, 0, newUsers, 0, count);
            users = newUsers;
        }

        users[count++] = user;
    }

    @Override
    public User getUserById(Long id) {
        for (int i = 0; i < count; i++) {
            if (users[i].getId().equals(id)) {
                return users[i];
            }
        }

        throw new UserNotFoundException("User with id " + id + " not found");
    }

    @Override
    public User getUserByIndex(int index) {
        if (index >= 0 && index < count) {
            return users[index];
        }

        throw new UserNotFoundException("User with index " + index + " not found");
    }

    @Override
    public int getUserCount() {
        return count;
    }
}
