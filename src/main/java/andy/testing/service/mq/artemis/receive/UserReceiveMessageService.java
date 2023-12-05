package andy.testing.service.mq.artemis.receive;

import andy.testing.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Profile("artemis")
public class UserReceiveMessageService {
    private JmsTemplate jms;
    @Autowired
    public UserReceiveMessageService(JmsTemplate jms) {
        this.jms = jms;

    }

    public UserEntity receiveUser() {
        return (UserEntity) jms.receiveAndConvert("user.queue");
    }

}
