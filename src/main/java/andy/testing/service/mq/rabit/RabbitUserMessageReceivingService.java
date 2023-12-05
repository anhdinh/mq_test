package andy.testing.service.mq.rabit;

import andy.testing.entity.UserEntity;
import andy.testing.service.mq.IUserMessageReceivingService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("rabbit")
@Log4j2
public class RabbitUserMessageReceivingService implements IUserMessageReceivingService {

    private RabbitTemplate rabbit;
    private MessageConverter converter;
    @Autowired
    public RabbitUserMessageReceivingService(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
        this.converter = rabbit.getMessageConverter();
    }
    public UserEntity receiveUser() {
        Message message = rabbit.receive("user.queue");
        return message != null
                ? (UserEntity) converter.fromMessage(message)
                : null;
    }
    @RabbitListener(queues="user.queue")
    public void receiveUserByListener(UserEntity userEntity){
        log.log(Level.INFO,userEntity.getEmail());
    }
}
