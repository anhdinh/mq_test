package andy.testing.service.mq.kafka;

import andy.testing.entity.UserEntity;
import andy.testing.service.mq.IUserMessageSendingService;
import andy.testing.service.mq.QueueList;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Profile("kafka")
public class KafkaUserSendingService implements IUserMessageSendingService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaUserSendingService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendUser(UserEntity userEntity) {
        kafkaTemplate.send(QueueList.KAFKA_USER_TOPIC, userEntity);
    }

    @KafkaListener(topics = QueueList.KAFKA_USER_TOPIC, groupId = "love")
    public void handle( @Payload UserEntity user) {
        System.out.println("user email"+user.getEmail());
    }


}
