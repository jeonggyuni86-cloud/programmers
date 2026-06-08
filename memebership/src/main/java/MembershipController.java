import java.util.*;

public class MembershipController implements Membership{
    private final Map<Grade, Set<Member>> members;

    MembershipController() {
        members = new LinkedHashMap<>();
        for(Grade grade : Grade.values())
            members.computeIfAbsent(grade, k -> new LinkedHashSet<>());
    }

    public String[] getPricePlan() {
        List<String> list = new ArrayList<>();
        int idx = 1;
        for(Map.Entry<Grade, Set<Member>> entry : members.entrySet()) {
            Grade grade = entry.getKey();
            String str = "[" + idx + "]" + grade.name() + " : "
                    + entry.getValue().size()
                    + " / "
                    + grade.LIMIT + "명";
            list.add(str);
        }
        return list.toArray(String[]::new);
    }

    @Override
    public boolean addMember(int idx, Member member) {
        Grade grade = Grade.fromIdx(idx);
        if(grade == null) return false;
        return this.addMember(grade, member);
    }

    public boolean addMember(Grade grade, Member member) {
        Set<Member> memberSet = members.getOrDefault(grade, Collections.emptySet());
        if (memberSet.contains(member)) return false;
        if (!checkEmail(memberSet, member.email)) return false;

        memberSet.add(member);
        members.put(grade, memberSet);
        return true;
    }

    private boolean checkEmail(Set<Member> set, String email) {
        for(Member member : set)
            if(email.equals(member.email)) return false;
        return true;
    }

    @Override
    public Member[] selectEmail(String email) {
        List<Member> list = new ArrayList<>();
        for(Map.Entry<Grade, Set<Member>> entry : members.entrySet()) {
            for(Member member : entry.getValue()) {
                if(member.email.equals(email))
                    list.add(member);
            }
        }
        return list.toArray(Member[]::new);
    }

    @Override
    public Member[] selectName(String name) {
        List<Member> list = new ArrayList<>();
        for(Map.Entry<Grade, Set<Member>> entry : members.entrySet()) {
            for(Member member : entry.getValue()) {
                if(member.name.equals(name))
                    list.add(member);
            }
        }
        return list.toArray(Member[]::new);
    }

    @Override
    public Member[] selectAll() {
        List<Member> list = new ArrayList<>();
        for(Map.Entry<Grade, Set<Member>> entry : members.entrySet())
            list.addAll(entry.getValue());
        return list.toArray(Member[]::new);
    }

    @Override
    public boolean updateMember(Member before, Member update) {
        for(Map.Entry<Grade, Set<Member>> entry : members.entrySet()) {
            Set<Member> set = entry.getValue();
            if(set.contains(before)) {
                set.remove(before);
                set.add(update);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteMember(Member member) {
        for(Map.Entry<Grade, Set<Member>> entry : members.entrySet()) {
            Set<Member> set = entry.getValue();
            if(set.contains(member)) {
                set.remove(member);
                return true;
            }
        }
        return false;
    }
}
