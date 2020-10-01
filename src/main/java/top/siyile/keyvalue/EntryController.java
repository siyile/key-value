package top.siyile.keyvalue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class EntryController {
    private final EntryRepository repository;
    private final StringRedisTemplate stringRedisTemplate;
    private static final String CONTENT_KEY = "CONTENT_KEY";
    public static final String CONTENT_SIZE_KEY = "CONTENT_SIZE_KEY";
    @Autowired
    public EntryController(EntryRepository repository, StringRedisTemplate template) {
        this.repository = repository;
        this.stringRedisTemplate = template;
    }

    private String content(Map<String, Integer> map) {
        StringBuilder sb = new StringBuilder();
        final String template = "%s: %s ";
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            sb.append(String.format(template, entry.getKey(), entry.getValue()));
        }

        return sb.toString();
    }

    @GetMapping("/counter")
    public CounterResponse counter() {
        Boolean hasKey = stringRedisTemplate.hasKey(CONTENT_KEY);
        Boolean hasKeySize = stringRedisTemplate.hasKey(CONTENT_SIZE_KEY);
        if (hasKey != null && hasKey && hasKeySize != null && hasKeySize) {
            System.out.println("GET KEY CONTENT CACHE");
            String size = stringRedisTemplate.opsForValue().get(CONTENT_SIZE_KEY);
            String content = stringRedisTemplate.opsForValue().get(CONTENT_KEY);
            assert size != null;
            return new CounterResponse(Integer.parseInt(size), content);
        }
        List<Entry> entries = repository.findAll();
        Map<String, Integer> map = new HashMap<>();
        for (Entry entry : entries) {
            map.put(entry.value, map.getOrDefault(entry.value, 0) + 1);
        }
        String content = content(map);
        stringRedisTemplate.opsForValue().set(CONTENT_KEY, content);
        stringRedisTemplate.expire(CONTENT_KEY, 10, TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set(CONTENT_SIZE_KEY, String.valueOf(map.size()));
        stringRedisTemplate.expire(CONTENT_SIZE_KEY, 10, TimeUnit.SECONDS);
        System.out.println("SET CONTENT CACHE");
        return new CounterResponse(map.size(), content(map));
    }
}
