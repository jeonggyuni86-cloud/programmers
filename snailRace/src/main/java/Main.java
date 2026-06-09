public class Main {
    static void main(String[] args) {
        Race race = new Race();
        Snail s1 = new Snail("달팽이1", race);
        Snail s2 = new Snail("달팽이2", race);
        Snail s3 = new Snail("달팽이3", race);

        s1.start();
        s2.start();
        s3.start();

        try {
            s1.join();
            s2.join();
            s3.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        race.printRanking();

    }
}
