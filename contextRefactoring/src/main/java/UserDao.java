public class UserDao {
    private final Database db;
    public UserDao(Database db) {
        this.db = db;
    }

    void context(StatementStrategy strategy) {
        db.open();
        strategy.run(db);
        db.close();
    }

    void add(User user) {
        var strategy = new StatementStrategy() {
            @Override
            public void run(Database db) {
                db.getUsers().add(user);
                System.out.println("===== 익명 클래스 add =====");
            }
        };
        context(strategy);
    }

    void deleteAll() {
        var strategy = new StatementStrategy() {
            @Override
            public void run(Database db) {
                db.getUsers().clear();
                System.out.println("===== 익명 클래스 deleteAll =====");
            }
        };
        context(strategy);
    }

    void deleteAll(Object dummy) {
        context(db -> {
            db.getUsers().clear();
            System.out.println("===== 람다 deleteAll =====");
        });
    }

    void add(User user, Object dummy) {
        context(db -> {
            db.getUsers().add(user);
            System.out.println("===== 람다 add =====");
        });
    }
}
