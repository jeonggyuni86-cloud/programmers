package stream;

import java.util.List;

public record Order(int id, List<String> items) {
}
