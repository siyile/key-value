package top.siyile.keyvalue;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("key-value-client")
public interface EntryClient {
    @GetMapping("/counter")
    CounterResponse counter();
}
