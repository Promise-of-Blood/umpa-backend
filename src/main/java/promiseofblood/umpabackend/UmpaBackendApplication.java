package promiseofblood.umpabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class UmpaBackendApplication {

    @RequestMapping("/")
    String hello() {
        return "Hi";
    }

    public static void main(String[] args) {
        SpringApplication.run(UmpaBackendApplication.class, args);
    }

}
