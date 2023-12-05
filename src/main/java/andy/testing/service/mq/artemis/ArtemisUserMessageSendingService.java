package andy.testing.service.mq.artemis;

import andy.testing.entity.UserEntity;
import andy.testing.service.mq.IUserMessageSendingService;
import andy.testing.service.mq.QueueList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Profile("artemis")
public class ArtemisUserMessageSendingService implements IUserMessageSendingService {

    private JmsTemplate jms;



    @Autowired
    public ArtemisUserMessageSendingService(JmsTemplate jms) {
        this.jms = jms;
    }

    public void sendUser(UserEntity order) {
        jms.convertAndSend(QueueList.USER_QUEUE,order, message -> {
            message.setStringProperty("X_ORDER_SOURCE","WEB");
            return message;
        });
    }

}
