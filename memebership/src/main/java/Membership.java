public interface Membership {
    String[] getPricePlan();
    boolean addMember(int idx, Member member);
    Member[] selectEmail(String email);
    Member[] selectName(String name);
    Member[] selectAll();
    boolean updateMember(Member before, Member update);
    boolean deleteMember(Member member);
    String available(int idx);
}
