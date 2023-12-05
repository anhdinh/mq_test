package andy.testing.service.mq.rabit;

import andy.testing.entity.UserEntity;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("rabbit")
public class RabbitUserMessagingService {
    private final  RabbitTemplate rabbit;

    public RabbitUserMessagingService(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    public void sendUserToQueue(UserEntity userEntity){
        var converter =  rabbit.getMessageConverter();
        var props = new MessageProperties();
        props.setHeader("X_ORDER_SOURCE", "WEB");
        var message = converter.toMessage(userEntity,props);
        rabbit.convertAndSend(message);

    }
}
