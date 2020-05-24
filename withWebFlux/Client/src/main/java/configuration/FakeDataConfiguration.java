package configuration;

import com.github.javafaker.Faker;
import com.github.javafaker.RickAndMorty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FakeDataConfiguration {
    @Bean
    public RickAndMorty faker() {
        return new Faker().rickAndMorty();
    }
}
