package ApiEx.withWebFlux.services.smssender;

import ApiEx.withWebFlux.repositories.SmsRepository;
import ApiEx.withWebFlux.services.smslistener.MessageReceiverListenerImpl;
import ApiEx.withWebFlux.forms.SmsForm;
import ApiEx.withWebFlux.models.SmsModels;
import ApiEx.withWebFlux.repositories.SmsRepository;
import ApiEx.withWebFlux.services.smslistener.MessageReceiverListenerImpl;
import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.*;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.util.AbsoluteTimeFormatter;
import org.jsmpp.util.DeliveryReceiptState;
import org.jsmpp.util.TimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class MessageSenderImpl implements MessageSender {
    @Autowired
    private SmsRepository smsRepository;
    @Autowired
    private MessageReceiverListenerImpl messageReceiverListener;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSenderImpl.class);

    private static final TimeFormatter TIME_FORMATTER = new AbsoluteTimeFormatter();

    private static final String SERVICE_TYPE = "CMT";

    public static boolean resultSuccess=true;

    @Transactional
    public boolean saveAndSend(SmsForm smsForm) {

        SmsModels sms = SmsModels.builder()
                .clientRequestId(smsForm.getClientRequestId())
                .senderName(smsForm.getSender().getName().getEn())
                .recTel(smsForm.getRecipient().get(0).getTel().split(":")[1])
                .messageRu(smsForm.getMessage().getRu())
                .messageUz(smsForm.getMessage().getUz())
                .build();
        smsRepository.save(sms);
        broadcastMessage(sms.getMessageRu(), sms.getRecTel());
        return resultSuccess;
    }

    @Override
    public List<SmsModels> findAll() {
        return smsRepository.findAll();
    }


    @Override
    public void broadcastMessage(String messageRu, String number) {
        LOGGER.info("Broadcasting smssender");

        SubmitMultiResult result = null;
        Address[] addresses = prepareAddress(number);
        SMPPSession session = initSession();


        if (session != null) {
            try {
                String address = "998914064902";

                result = session.submitMultiple(
                        SERVICE_TYPE, TypeOfNumber.NATIONAL, NumberingPlanIndicator.UNKNOWN, address,
                        addresses, new ESMClass(), (byte) 0, (byte) 1, TIME_FORMATTER.format(new Date()), null,
                        new RegisteredDelivery(SMSCDeliveryReceipt.SUCCESS_FAILURE), ReplaceIfPresentFlag.REPLACE,
                        new GeneralDataCoding(Alphabet.ALPHA_DEFAULT, MessageClass.CLASS1, false), (byte) 0,
                        messageRu.getBytes());

                LOGGER.info("Messages submitted, result is {}", result);
                Thread.sleep(1000);

            } catch (PDUException e) {
                LOGGER.error("Invalid PDU parameter", e);
                resultSuccess=false;
            } catch (ResponseTimeoutException e) {
                LOGGER.error("Response timeout", e);
                resultSuccess=false;
            } catch (InvalidResponseException e) {
                LOGGER.error("Receive invalid response", e);
                resultSuccess=false;
            } catch (IOException | IllegalArgumentException | InterruptedException | NegativeResponseException e) {
                LOGGER.error("Exception occured submitting SMPP request", e);
                resultSuccess=false;
            }
        } else {
            LOGGER.error("Session creation failed with SMPP broker.");
            resultSuccess=false;
        }
        if (result != null && result.getUnsuccessDeliveries() != null && result.getUnsuccessDeliveries().length > 0) {
            LOGGER.error(DeliveryReceiptState.getByName(String.valueOf(result.getUnsuccessDeliveries()[0].getErrorStatusCode())).name() + " - " + result.getMessageId());
            resultSuccess=false;
        } else {
            LOGGER.info("Pushed message to broker successfully");
        }
//        if(session != null) {
//            session.unbindAndClose();
//        }
    }

    @Override
    public Address[] prepareAddress(String number) {
        Address []address =new Address[1];
        Address addr= new Address(TypeOfNumber.NATIONAL, NumberingPlanIndicator.UNKNOWN,number);
        address[0] =addr;

        return address;
    }

    @Override
    @PostConstruct
    public SMPPSession initSession() {
        SMPPSession session = new SMPPSession();
        try {
            session.setMessageReceiverListener(messageReceiverListener);
            String password = "password";
            String username = "username";
            int port = 8080;
            String smppIp = "000.00.00.000";
            String systemId = session.connectAndBind(smppIp,
                    port,
                    new BindParameter(
                            BindType.BIND_TRX,
                            username,
                            password,
                            "0",
                            TypeOfNumber.UNKNOWN,
                            NumberingPlanIndicator.UNKNOWN,
                            null));
            LOGGER.info("Connected with SMPP with system id {}", systemId);
        } catch (IOException e) {
            LOGGER.error("Exception with connect to SMSC", e);
            session = null;
            resultSuccess=false;
        }
        return session;
    }
}