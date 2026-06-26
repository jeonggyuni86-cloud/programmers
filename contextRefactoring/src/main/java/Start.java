public class Start {
    static void main(String[] args) {
        Database db = new Database();
        UserDao dao = new UserDao(db);

        dao.context(new AddStrategy(new User("142", "142")));
        System.out.println("=".repeat(30));

        dao.add(new User("123", "123"));
        System.out.println("=".repeat(30));

        dao.add(new User("456", "456"), null);
        System.out.println("=".repeat(30));


        dao.context(new DeleteAllStrategy());
        System.out.println("=".repeat(30));

        dao.deleteAll();
        System.out.println("=".repeat(30));

        dao.deleteAll(null);
        System.out.println("=".repeat(30));

    }
}
