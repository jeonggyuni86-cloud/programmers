public interface AccountBook {
    void addAccount(String date, Item item);
    void showAccount();
    void deleteAll(String date);
    void deleteItem(String date, int idx);
}
