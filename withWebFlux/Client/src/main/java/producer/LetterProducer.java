package producer;

import com.github.javafaker.RickAndMorty;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import model.Letter;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Service
@Setter
@RequiredArgsConstructor
public class LetterProducer {
    private final RickAndlllMorty faker;


    @SneakyThrows
    public Letter getLetter() {
        return randomLetter();
    }

    LinkedBlockingQueue letterQueue = new LinkedBlockingQueue();

    public Flux<Letter> letterFlux() {
        return Flux.generate(synchronousSink -> synchronousSink.next(randomLetter()));
    }

    private Letter randomLetter() {
        String character = faker.character();
        return Letter.builder()
                .content(faker.quote())
                .location(faker.location())
                .signature(character)
                ._original(character)
                .build();
    }


}
