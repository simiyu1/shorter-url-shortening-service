package com.shorterurl.shorterurl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ShorterurlApplication.class, properties = "spring.profiles.active=test")
class ShorterurlApplicationTests {

    @Test
    void contextLoads() {
    }

}