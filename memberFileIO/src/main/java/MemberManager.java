import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MemberManager {
    private static final String FILE_NAME = "members.txt";
    private final List<Member> members;

    public MemberManager() {
        members = new ArrayList<>();
        this.load();
    }

    public void add(Member member) {
        members.add(member);
        save();
    }

    public List<Member> findAll() {
        return List.copyOf(members);
    }

    public boolean update(int idx, Member newMember) {
        if(!isValid(idx)) return false;

        members.set(idx, newMember);
        save();
        return true;
    }

    public boolean delete(int idx) {
        if(!isValid(idx)) return false;
        members.remove(idx);
        save();
        return true;
    }

    private boolean isValid(int idx) {
        return idx >= 0 && idx < members.size();
    }
    public void save() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, false))) {
            for(Member member : members) {
                writer.write(member.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Save Fail");
        }
    }

    private void load() {
        File file = new File(FILE_NAME);
        if(!file.exists()) return;

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if(line.isBlank()) continue;
                String[] data = line.split(",");
                if(data.length != 4) continue;
                members.add(MemberFactory.from(data[0], data[1], data[2], data[3]));
            }

        } catch(IOException e) {
            System.out.println("Error Reading");
        }
    }
}
