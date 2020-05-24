package ApiEx.withWebFlux.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sms_table")
public class SmsModels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="client_request_id")
    private String clientRequestId;

    @Column(name="sender_name")
    private String senderName;

    @Column(name="rec_tel")
    private String recTel;

    @Column(name="message_uz")
    private String messageUz;

    @Column(name="message_ru")
    private String messageRu;
}
