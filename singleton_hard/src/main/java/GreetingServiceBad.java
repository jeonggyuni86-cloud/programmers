public class GreetingServiceBad {
    private static GreetingServiceBad instance;
    private GreetingServiceBad() {}
    public static synchronized GreetingServiceBad getInstance() {
        if (instance == null) {
            instance = new GreetingServiceBad();
        }
        return instance;
    }

    private String name;
    public String greet(String reqName) {
        this.name = reqName;
        try {
            Thread.sleep(500);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this.name;
    }
}
