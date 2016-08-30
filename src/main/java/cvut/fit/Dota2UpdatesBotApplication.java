package cvut.fit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.WebApplicationContext;

@SpringBootApplication
@EnableScheduling
public class Dota2UpdatesBotApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Dota2UpdatesBotApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Dota2UpdatesBotApplication.class);
    }

}
