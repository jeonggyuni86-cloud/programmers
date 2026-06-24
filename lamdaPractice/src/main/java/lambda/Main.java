package lambda;

public class Main {
    static void main(String[] args) {
        Operation add = (a, b) -> a + b;
        Operation sub = (a, b) -> a - b;

        Add add1 = new Add();

        System.out.println("lambda " + add.apply(10, 20));
        System.out.println("lambda " + sub.apply(10, 20));

        System.out.println("class " + add1.apply(10, 20));


        String str = "lambda " + add.apply(10, 20);
        Printer printer = System.out::println;
        printer.print(str);

    }
}
