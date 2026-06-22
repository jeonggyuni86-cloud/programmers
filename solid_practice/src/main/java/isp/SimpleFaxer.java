package isp;

public class SimpleFaxer implements Faxer {
    @Override
    public void fax() {
        System.out.println("팩스 실행!");
    }
}
