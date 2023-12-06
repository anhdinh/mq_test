package andy.testing.service.mq.kafka;

import andy.testing.entity.UserEntity;
import andy.testing.service.mq.IUserMessageReceivingService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("kafka")
public class KafkaUserReceivingService implements IUserMessageReceivingService {
    @Override
    public UserEntity receiveUser() {
        throw new UnsupportedOperationException();
    }
}
