package SmsApi.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SmsForm {

    private String clientRequestId;

    private Sender sender;

    private List<Recipient> recipient;

    private Message message;
}
