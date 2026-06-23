package hollywood;

public class Button {
    private final ClickListener listener;
    public Button(ClickListener listener) {
        this.listener = listener;
    }

    public void press() {
        System.out.println("[시스템] 버튼이 눌렸습니다");
        listener.onClick();
    }
}
