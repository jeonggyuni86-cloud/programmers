public class AddStrategy implements StatementStrategy {
    private final User user;
    AddStrategy(User user) {
        this.user = user;
    }
    @Override
    public void run(Database db) {
        db.getUsers().add(user);
        System.out.println("===== 별도 전략 클래스 add =====");
    }
}
