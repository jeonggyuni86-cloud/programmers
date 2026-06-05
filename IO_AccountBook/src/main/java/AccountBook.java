public interface AccountBook {
    boolean addAccount(String date, Item item);
    String[] showDate();
    String[] showAccount(String[] dates, int idx);
    boolean deleteAccount(String[] dates, int dateIdx, String[] account, int idx);
    boolean deleteFile(String[] dates, int dateIdx);
}
