package andy.testing.service.mq.artemis.receive;

import andy.testing.entity.UserEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Component
@Profile("artemis")
public class UserListenerMessageService {

    @JmsListener(destination = QueueList.USER_QUEUE)
    public void receiveUser(UserEntity userEntity){
        System.out.println("-------listener1------"+userEntity.getEmail());
    }

    @JmsListener(destination = QueueList.USER_QUEUE)
    public void receiveUser2(UserEntity userEntity){
        System.out.println("-------listener2------"+userEntity.getEmail());
    }


    @JmsListener(destination = QueueList.USER_QUEUE)
    public void receiveUser3(UserEntity userEntity){
        System.out.println("-------listener3------"+userEntity.getEmail());
    }

}
