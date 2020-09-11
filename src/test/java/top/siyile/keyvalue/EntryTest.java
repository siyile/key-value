package top.siyile.keyvalue;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class EntryTest {
    private final EntryController controller;
    @Autowired
    public EntryTest(EntryController controller) {
        this.controller = controller;
    }

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void counter(@Autowired EntryRepository repository) throws Exception {
        repository.save(new Entry("Dream", "MeowMeow"));
        repository.findAll().forEach(entry -> log.info(entry.toString()));
        assertThat(controller.counter()).extracting("number").isEqualTo(3);
        assertThat(controller.counter().getResults()).contains("Meow: 2", "MeowMeow: 1");
    }
}
