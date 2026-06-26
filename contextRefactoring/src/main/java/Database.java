import java.util.ArrayList;
import java.util.List;

public class Database {
    private final List<User> users = new ArrayList<>();

    public void open() {
        System.out.println("[컨텍스트] 연결 열기");
    }
    public void close() {
        System.out.println("[컨텍스트] 연결 닫기");
    }

    public List<User> getUsers() {
        return users;
    }
}
