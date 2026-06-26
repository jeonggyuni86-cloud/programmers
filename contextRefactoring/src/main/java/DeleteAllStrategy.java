public class DeleteAllStrategy implements StatementStrategy {
    @Override
    public void run(Database db) {
        db.getUsers().clear();
        System.out.println("===== 별도 전략 클래스 deleteAll =====");
    }
}
