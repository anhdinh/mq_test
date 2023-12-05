package andy.testing.service.mq.artemis;

import andy.testing.entity.UserEntity;
import andy.testing.service.mq.IUserMessageReceivingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Profile("artemis")
public class ArtemisUserReceiveMessageService implements IUserMessageReceivingService {
    private JmsTemplate jms;
    @Autowired
    public ArtemisUserReceiveMessageService(JmsTemplate jms) {
        this.jms = jms;

    }

    public UserEntity receiveUser() {
        return (UserEntity) jms.receiveAndConvert("user.queue");
    }

}
