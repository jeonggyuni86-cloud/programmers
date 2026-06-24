package stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {
    static void main(String[] args) {
        var products = new ArrayList<>(Arrays.asList(
                new Product("연필", 500), new Product("공책", 1200), new Product("지우개", 300),
                new Product("필통", 3000), new Product("볼펜", 800)));

        products.forEach(System.out::println);

        System.out.println("=".repeat(30));

        products.stream()
                .filter(p -> p.price() > 1000)
                .forEach(System.out::println);

        System.out.println("=".repeat(30));

        products.stream().map(Product::name).forEach(System.out::println);

        System.out.println("=".repeat(30));

        var orders = Arrays.asList(
                new Order(1, Arrays.asList("연필", "공책")),
                new Order(2, Arrays.asList("필통", "볼펜", "공책"))
        );

        List<List<String>> byMap = orders.stream().map(Order::items).toList();
        System.out.println("map     : " + byMap);

        System.out.println("=".repeat(30));

        var flatMap = byMap.stream().flatMap(List::stream).toList();
        System.out.println("flatMap : " + flatMap);

        System.out.println("=".repeat(30));

        //step 5

        List<String> productNames = products.stream()
                .filter(p -> p.price() >= 1000)
                .map(Product::name)
                .toList();

        for(var name: productNames)
            System.out.printf("%s\t", name);

        System.out.println();
        System.out.println("=".repeat(30));

        //Step 6
        long count = products.stream().filter(p -> p.price() >= 1000).count();
        int sum = products.stream().mapToInt(Product::price).sum();
        double average = products.stream().mapToDouble(Product::price).average().orElse(-1);
        List<Product> sorted = products.stream().sorted(Comparator.comparingInt(Product::price)).toList();

        System.out.println("count : " + count);
        System.out.println("sum : " + sum);
        System.out.println("average : " + average);
        System.out.println("sorted : " + sorted);

        //도전과제
        System.out.println("=".repeat(30));
        for(var name : products.stream().filter(p -> p.price() <= 500).map(Product::name).toList())
            System.out.print(name + "\t");
        System.out.println();
        System.out.println("=".repeat(30));

        Product expensive = products.stream().max(Comparator.comparing(Product::price)).orElse(null);
        System.out.println("expensive : " + expensive);
        System.out.println("=".repeat(30));

        flatMap.stream().distinct().forEach(s -> System.out.print(s + "\t"));
        System.out.println();
        System.out.println("=".repeat(30));

    }

}
