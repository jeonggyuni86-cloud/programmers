public class Main {
    static int badMismatch = 0;
    static int goodMismatch = 0;

    public static void main(String[] args) throws InterruptedException {
        int N = 30;

        Thread[] t1 = new Thread[N];
        for (int i = 0; i < N; i++) {
            final String myName = "손님" + i;
            t1[i] = new Thread(() -> {                       // 스레드가 할 일
                String r = GreetingServiceBad.getInstance().greet(myName);
                if (!r.equals(myName)) {                     // 내 이름이 안 돌아오면 = 엉킴
                    synchronized (Main.class) { badMismatch++; }
                }
            });
        }

        Thread[] t2 = new Thread[N];
        for (int i = 0; i < N; i++) {
            final String myName = "손님" + i;
            t2[i] = new Thread(() -> {                       // 스레드가 할 일
                String r = GreetingServiceGood.getInstance().greet(myName);
                if (!r.equals(myName)) {                     // 내 이름이 안 돌아오면 = 엉킴
                    synchronized (Main.class) { goodMismatch++; }
                }
            });
        }


        for (Thread t : t1) t.start();
        for (Thread t : t1) t.join();

        for (Thread t : t2) t.start();
        for (Thread t : t2) t.join();
        System.out.println("[필드에 저장] 엉킴: " + badMismatch + " / " + N);
        System.out.println("[파라미터로]  엉킴: " + goodMismatch + " / " + N);
    }
}