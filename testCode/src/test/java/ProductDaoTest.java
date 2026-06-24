import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoTest {
    private ProductDao dao = new ProductDao();

    @BeforeEach
    void setUp() {
        dao.deleteAll();
    }

    private Product newProduct(String id, String name, int price) {
        Product p = new Product();
        p.setId(id); p.setName(name); p.setPrice(price);
        return p;
    }

    @Test
    void add() {
        assertEquals(0, dao.getCount());
        Product product = newProduct("p1", "연필", 500);

        dao.add(product);

        assertEquals(1, dao.getCount());
    }

    @Test
    void get() {
        Product product = newProduct("p1", "연필", 500);
        dao.add(product);

        Product found = dao.get("p1");

        assertEquals(product.getName(),  found.getName());
        assertEquals(product.getPrice(), found.getPrice());
    }

    @Test
    void add_중복_id_예외() {
        final Product product = newProduct("dup", "지우개", 300);
        dao.add(product);                       // 첫 저장은 정상

        Executable action = new Executable() {  // 예외가 날 코드를 익명 클래스로
            @Override
            public void execute() {
                dao.add(product);               // 같은 id 재저장 → 예외!
            }
        };
        assertThrows(IllegalStateException.class, action);
    }

    @Test
    void get_없는_id_예외() {
        Executable action = new Executable() {
            @Override
            public void execute() {
                dao.get("없는_id");             // 행 없음 → 예외!
            }
        };
        assertThrows(NoSuchElementException.class, action);
    }

    @Disabled("일부러 틀린 기대값을 넣은 학습용 실패 예제")
    @Test
    void 일부러_실패하는_테스트() {
        dao.add(newProduct("fail_demo", "공책", 1000));
        assertEquals(2, dao.getCount());   // 실제는 1건인데 2 기대 → 실패
    }
}