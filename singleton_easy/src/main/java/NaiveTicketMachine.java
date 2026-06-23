public class NaiveTicketMachine {
    private int lastNumber = 0;
    public int issue() {
        lastNumber++;
        return lastNumber;
    }
}
