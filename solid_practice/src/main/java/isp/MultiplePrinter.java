package isp;

public class MultiplePrinter implements Printer, Faxer {
    @Override
    public void print() {
        System.out.println("프린트 실행 중");
    }
    @Override
    public void fax() {
        System.out.println("팩스 실행 중");
    }
}
