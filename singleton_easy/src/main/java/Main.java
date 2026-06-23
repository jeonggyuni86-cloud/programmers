public class Main {
    static void main(String[] args) {
        // =============== Naive ================
        NaiveTicketMachine a = new NaiveTicketMachine();
        NaiveTicketMachine b = new NaiveTicketMachine();
        System.out.println(a.issue());
        System.out.println(b.issue());
        System.out.println(a.equals(b));


        // =============== Singleton ================

        TicketMachine t1 = TicketMachine.getInstance();
        TicketMachine t2 = TicketMachine.getInstance();
        System.out.println(a.issue());
        System.out.println(b.issue());
        System.out.println(t1.equals(t2));
    }
}
