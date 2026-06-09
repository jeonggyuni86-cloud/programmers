import java.util.Random;

public class Snail extends Thread {
    private final String name;
    private int position;

    private final Random random;

        this.name = name;
        this.random = new Random();
        this.position = 0;
    }

    @Override
    public void run() {
        while (position < FINISH) {
            position += random.nextInt(3) + 1;
            position = Math.min(position, FINISH);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}