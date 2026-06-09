import java.util.Random;

public class Snail extends Thread {
    private final String name;
    private int position;

    private static final int FINISH = 30;
    private final Random random;
    private final Race race;
    private int rank = 0;

    Snail(String name, Race race) {
        this.name = name;
        this.random = new Random();
        this.position = 0;
        this.race = race;
    }

    @Override
    public void run() {
        while (position < FINISH) {
            position += random.nextInt(3) + 1;
            position = Math.min(position, FINISH);
            printProgress();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(position == FINISH) {
                rank = race.finish(name);
            }
        }
    }
    private void printProgress() {
        StringBuilder sb = new StringBuilder();
        sb.repeat("=", position);
        sb.append(">");
        System.out.println(name + " : " + sb + " " + position);
    }

}