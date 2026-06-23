class GreetingServiceGood {
    private static GreetingServiceGood instance;
    private GreetingServiceGood() {}

    static GreetingServiceGood getInstance() {
        if(instance == null) instance = new GreetingServiceGood();
        return instance;
    }

    String greet(String reqName) {
        try { Thread.sleep(5); } catch (InterruptedException e) {}
        return reqName;
    }
}
