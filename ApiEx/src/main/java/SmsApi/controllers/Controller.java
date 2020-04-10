package SmsApi.controllers;

import SmsApi.forms.SmsForm;
import SmsApi.services.sms.SmsService;
import SmsApi.transfer.SmsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static SmsApi.transfer.SmsDto.from;


@RestController
public class Controller {
    @Autowired
    private SmsService smsService;

    @PostMapping("/smsapi")
    public ResponseEntity<Object> saveAndSendSms(@RequestBody SmsForm smsForm) {
        smsService.saveAndSend(smsForm);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/allsms")
    public List<SmsDto> getSms(){
        return from(smsService.findAll());
    }
}