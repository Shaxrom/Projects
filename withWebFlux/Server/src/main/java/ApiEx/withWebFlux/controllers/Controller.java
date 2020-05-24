package ApiEx.withWebFlux.controllers;

import ApiEx.withWebFlux.forms.SmsForm;
import ApiEx.withWebFlux.services.smssender.MessageSender;
import ApiEx.withWebFlux.transfer.SmsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import static ApiEx.withWebFlux.transfer.SmsDto.from;


@RestController
public class Controller {
    @Autowired
    private MessageSender messageSender;
    @Autowired
    private ThreadPoolExecutor executor;


    @PostMapping(value = "/smsapi",consumes = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<Void> saveAndSendSms(@RequestBody Flux<SmsForm> smsForm) {
        return smsForm.flatMap(smsForm1 -> Mono.fromCallable(()-> messageSender.saveAndSend(smsForm1))
                .subscribeOn(Schedulers.fromExecutor(executor)),
                                executor.getMaximumPoolSize())
                .log()
                .then();
    }

    @GetMapping("/allsms")
    public List<SmsDto> getSms(){
        return from(messageSender.findAll());
    }
}