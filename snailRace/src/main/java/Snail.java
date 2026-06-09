import java.util.Random;

public class Snail extends Thread {
    private final String name;
    private int position;

    private final int FINISH = 30;
    private final Random random;

    Snail(String name) {
        this.name = name;
        this.random = new Random();
        this.position = 0;
    }

    @Override
    public void run() {
        while (position < FINISH) {
            position += random.nextInt(3) + 1;
            position = Math.min(position, FINISH);
            System.out.println(name + "위치 : " + position);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}