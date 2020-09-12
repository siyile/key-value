package top.siyile.keyvalue;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    public Health greeting() {
        return new Health("UP");
    }
}
