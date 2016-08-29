package cvut.fit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Dota2UpdatesBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(Dota2UpdatesBotApplication.class, args);
    }


}
