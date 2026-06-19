import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberManager {

    private Connection connection() {
        String url = ID.URL;
        String user = ID.ID;
        String password = ID.PW;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Member> selectAll() {
        List<Member> members = new ArrayList<>();

        String query = "SELECT id, grade, name, email, phone FROM MEMBER";
        try (   var conn = connection();
                var pStat = conn.prepareStatement(query);
                var rs = pStat.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String grade = rs.getString("grade");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                members.add(MemberFactory.from(id, grade, name, email, phone));
            }
            return members;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private long countByGrade(Grade grade) {
        String query = "SELECT COUNT(*) AS cnt FROM MEMBER WHERE grade = ?";
        try(    var conn = connection();
                var pStat = conn.prepareStatement(query)) {
            pStat.setString(1, grade.name());
            var rs = pStat.executeQuery();
            if (rs.next())
                return rs.getLong("cnt");
            return 0;

        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Member selectById(int id) {
        String query = "SELECT * FROM MEMBER WHERE id = ?";
        try (   var conn = connection();
                var pStat = conn.prepareStatement(query)) {

            pStat.setInt(1, id);
            var rs = pStat.executeQuery();
            if(rs.next()) {
                int id2 =  rs.getInt("id");
                String grade = rs.getString("grade");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                return MemberFactory.from(id2, grade, name, email, phone);
            }
            else {
                return null;
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Member selectByName(String name) {
        String query = "SELECT * FROM MEMBER WHERE name = ?";
        try (   var conn = connection();
                var pStat= conn.prepareStatement(query)) {
            pStat.setString(1, name);
            var rs = pStat.executeQuery();
            if(rs.next()) {
                int id =  rs.getInt("id");
                String grade = rs.getString("grade");
                String name2 = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                return MemberFactory.from(id, grade, name2, email, phone);
            } else {
                return null;
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Member selectByEmail(String email) {
        String query = "SELECT * FROM MEMBER WHERE email = ?";
        try(    var conn = connection();
                var pStat = conn.prepareStatement(query)) {
            pStat.setString(1, email);

            var rs = pStat.executeQuery();
            if(rs.next()) {
                int id =  rs.getInt("id");
                String grade = rs.getString("grade");
                String name = rs.getString("name");
                String email2 = rs.getString("email");
                String phone = rs.getString("phone");
                return MemberFactory.from(id, grade, name, email2, phone);
            } else {
                return null;
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void addMember(Member member) {
        Grade grade = member.grade();
        long cnt = countByGrade(grade);

        if(cnt >= grade.getLimit()) {
            System.out.println(grade.name() + " 정원 초과");
            return;
        }
        String query = "INSERT INTO MEMBER (grade, name, email, phone) VALUES (?, ?, ?, ?)";
        try(    var conn = connection();
                var pStat = conn.prepareStatement(query)) {
            pStat.setString(1, grade.name());
            pStat.setString(2, member.name());
            pStat.setString(3, member.email());
            pStat.setString(4, member.phone());
            pStat.executeUpdate();
        } catch(SQLException e) {
            System.out.println("중복된 이메일 입니다");
        }
    }

    public void updateMember(int id, String name, String email, String phone) {
        Member member = selectById(id);
        if(member == null) {
            System.out.println("해당 회원 없음");
            return;
        }

        String query = "UPDATE MEMBER SET name = ?, email = ?, phone = ? WHERE id = ?";
        try(    var conn = connection();
                var pStat = conn.prepareStatement(query)) {
            pStat.setString(1, name);
            pStat.setString(2, email);
            pStat.setString(3, phone);
            pStat.setInt(4, id);

            pStat.executeUpdate();

        }catch(SQLException e) {
            System.out.println("중복된 이메일 입니다");
        }
    }

    public void deleteMember(int id) {
        String query = "DELETE FROM MEMBER WHERE id = ?";
        Member member = selectById(id);
        if(member == null) {
            System.out.println("해당 회원이 없습니다.");
            return;
        }
        try(    var conn = connection();
                var pStat = conn.prepareStatement(query)) {

            pStat.setInt(1, id);
            pStat.executeUpdate();
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long countVIP() {
        return countByGrade(Grade.VIP);
    }
    public long countNormal() {
        return countByGrade(Grade.NORMAL);
    }

}