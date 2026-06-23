public class TicketMachine {
    private static TicketMachine instance;
    private int lastNumber = 0;

    private TicketMachine() {}
    public static synchronized TicketMachine getInstance() {
        if(instance == null) {
            instance = new TicketMachine();
        }
        return instance;
    }

    public int issue() {
        lastNumber++;
        return lastNumber;
    }
}
