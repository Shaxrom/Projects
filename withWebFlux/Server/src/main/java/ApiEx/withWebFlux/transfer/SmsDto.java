package ApiEx.withWebFlux.transfer;

import ApiEx.withWebFlux.models.SmsModels;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SmsDto {
    private Long id;
    private String clientRequestId;
    private String senderName;
    private String recTel;
    private String messageUz;
    private String messageRu;


   public static SmsDto from(SmsModels sms) {
        return SmsDto.builder()
                .id(sms.getId())
                .clientRequestId(sms.getClientRequestId())
                .senderName(sms.getSenderName())
                .recTel(sms.getRecTel())
                .messageUz(sms.getMessageUz())
                .messageRu(sms.getMessageRu())
                .build();
    }

    public static List<SmsDto> from(List<SmsModels> smsModelsList) {
        return smsModelsList.stream().map(SmsDto::from).collect(Collectors.toList());
    }
}