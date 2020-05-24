package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import configuration.AdjustmentProperties;
import io.rsocket.util.DefaultPayload;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import model.Letter;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import producer.LetterProducer;

import java.time.Duration;

@Slf4j
@Getter
@Service
public class LetterDistributor {
    private final LetterProducer       producer;
    private final AdjustmentProperties adjustmentProperties;
    private final WebClient.Builder    webClientBuilder;
    private final ObjectMapper         objectMapper;
    public LetterDistributor(LetterProducer producer,
                             WebClient.Builder webClientBuilder,
                             AdjustmentProperties adjustmentProperties,
                             ObjectMapper objectMapper) {
        this.producer = producer;
        this.adjustmentProperties = adjustmentProperties;
        this.objectMapper = objectMapper;
        this.webClientBuilder=webClientBuilder;
    }


    @EventListener(ApplicationStartedEvent.class)
    public void init() {
        webClientBuilder.baseUrl("http://localhost:8080/").build()
                .post().uri("/smsapi")
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .body(
                        producer.letterFlux()
                                .log()
                                .map(letter -> DefaultPayload.create(convertToByte(letter))),
                        Letter.class)
                .doOnError(throwable -> log.eror("some problem", throwable.getMessage()))
                .retryBackoff(Long.MAX_VALUE, Duration.ofMillis(500))
                .log()
                .subscribe(clientResponse -> log.info("clientResponse = " + clientResponse));

    }

    @SneakyThrows
    public byte[] convertToByte(Letter letter){return objectMapper.writeValueAsBytes(letter);}

}