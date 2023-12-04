package andy.testing.service.mq;

import andy.testing.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import static andy.testing.service.mq.QueueList.USER_QUEUE;

@Service
public class UserMessagingService {

    private JmsTemplate jms;



    @Autowired
    public UserMessagingService(JmsTemplate jms) {
        this.jms = jms;
    }

    public void convertAndSend(UserEntity order) {
        jms.convertAndSend(USER_QUEUE,order,message -> {
            message.setStringProperty("X_ORDER_SOURCE","WEB");
            return message;
        });
    }

}
