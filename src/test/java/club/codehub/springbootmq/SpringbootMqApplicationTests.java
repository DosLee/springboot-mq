package club.codehub.springbootmq;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Map;

@SpringBootTest
class SpringbootMqApplicationTests {

    @Resource
    private Map<String, String> queuesNames;

    @Test
    public void test28() {
        queuesNames.forEach((m, v) -> {
            System.out.println(m + ": " + v);
        });
    }

}
