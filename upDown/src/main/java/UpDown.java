import java.util.Random;

public class UpDown {
    private final int num;
    UpDown(int bound) {
        num = new Random().nextInt(bound) + 1;
    }

    public int isMatch(int sel) {
        return Integer.compare(sel, num);
    }

    public int getNum() {
        return num;
    }
}
