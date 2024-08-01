package day01.ex05;

public class UserIdsGenerator {
    private static Long userId = 0L;
    private static UserIdsGenerator userIdsGenerator;

    private UserIdsGenerator() {}

    public static UserIdsGenerator getInstance() {
        return userIdsGenerator = userIdsGenerator == null ? new UserIdsGenerator() : userIdsGenerator;
    }

    public Long generateUserId() { return ++userId; }
}
