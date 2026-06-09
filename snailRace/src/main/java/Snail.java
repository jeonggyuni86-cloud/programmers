import java.util.Random;

public class Snail extends Thread {
    private final String name;
    private int position;

    private final int FINISH;
    private final Random random;
    private final Race race;
    private final int speed;

    Snail(String name, Race race, int finish, int speed) {
        this.name = name;
        this.random = new Random();
        this.position = 0;
        this.race = race;
        this.FINISH = finish;
        this.speed = speed;
    }

    @Override
    public void run() {
        while (position < FINISH) {
            position += random.nextInt(speed) + 1;
            position = Math.min(position, FINISH);
            printProgress();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        race.finish(name);
    }

    private void printProgress() {
        race.printProgress(name, position);
    }
}