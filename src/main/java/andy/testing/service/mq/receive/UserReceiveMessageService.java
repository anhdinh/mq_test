package andy.testing.service.mq.receive;

import andy.testing.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserReceiveMessageService {
    private JmsTemplate jms;
    @Autowired
    public UserReceiveMessageService(JmsTemplate jms) {
        this.jms = jms;

    }

    public UserEntity receiveOrder() {
        return (UserEntity) jms.receiveAndConvert("user.queue");
    }

}
