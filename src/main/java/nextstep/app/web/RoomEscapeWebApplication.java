package nextstep.app.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "nextstep")
@SpringBootApplication
public class RoomEscapeWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(RoomEscapeWebApplication.class, args);
    }
}
