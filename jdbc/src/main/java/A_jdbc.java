import java.sql.*;

public class A_jdbc {
    public Connection connection() {
        String url = ID.URL;
        String user = ID.ID;
        String password = ID.PW;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database successfully");

            return connection;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertData(String name, int age, String phone) {
        String query = "INSERT INTO member (name, age, phone) VALUES (?, ?, ?)";
        try (
                Connection conn = connection();
                PreparedStatement pStat = conn.prepareStatement( query );
        ){
            pStat.setString(1, name);
            pStat.setInt(2, age);
            pStat.setString(3, phone);

            pStat.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void selectAll() {
        String query = "SELECT id, name, age, phone FROM member";

        try (
                Connection conn = connection();
                PreparedStatement pStat = conn.prepareStatement( query );
                ) {
            ResultSet rs = pStat.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String phone = rs.getString("phone");
                System.out.println(id + " " + name + " " + age + " " + phone);
                System.out.println("=".repeat(30));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void selectOne(int id) {
        String query = "SELECT id, name, age, phone FROM member WHERE id = ?";

        try(
                Connection conn = connection();
                PreparedStatement pStat = conn.prepareStatement( query );
                ) {
            pStat.setInt(1, id);
            ResultSet rs = pStat.executeQuery();
            if(rs.next()) {
                int id2 = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String phone = rs.getString("phone");
                System.out.println(id + " " + name + " " + age + " " + phone);
                System.out.println("=".repeat(30));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateData(int id, String name, int age, String phone) {
        String query = "UPDATE member SET name = ?, age = ?  , phone = ? WHERE id = ?";
        try (
                Connection conn = connection();
                PreparedStatement pStat = conn.prepareStatement( query );
                ) {
            pStat.setString(1, name);
            pStat.setInt(2, age);
            pStat.setString(3, phone);
            pStat.setInt(4, id);

            int result = pStat.executeUpdate();
            if(result > 0)
                System.out.println("Updated successfully");
            else
                System.out.println("Update failed");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteData(int id) {
        String query = "DELETE FROM member WHERE id = ?";
        try(
                Connection conn = connection();
                PreparedStatement pStat = conn.prepareStatement(query);
                ) {
            pStat.setInt(1, id);
            int result = pStat.executeUpdate();
            if(result > 0)
                System.out.println("Deleted successfully");
            else
                System.out.println("Delete failed");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    static void main(String[] args) {
        A_jdbc obj = new A_jdbc();
        // obj.updateData(2, "홍홍홍", 30, "010-8232-4545");
        obj.deleteData(2);
        obj.selectAll();
    }
}
