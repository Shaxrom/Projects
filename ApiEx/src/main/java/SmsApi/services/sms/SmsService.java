package SmsApi.services.sms;

import SmsApi.forms.SmsForm;
import SmsApi.models.SmsModels;
import org.jsmpp.bean.Address;
import org.jsmpp.session.SMPPSession;

import java.util.List;


public interface SmsService {
    void saveAndSend(SmsForm smsForm);

    void broadcastMessage(String message, String number);

    Address[] prepareAddress(String number);

    SMPPSession initSession();

    List<SmsModels> findAll();

}