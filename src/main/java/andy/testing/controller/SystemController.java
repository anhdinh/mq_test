package andy.testing.controller;

import andy.testing.service.mq.artemis.receive.UserReceiveMessageService;
import andy.testing.service.mq.rabit.RabbitUserMessagingReceiveService;
import jakarta.jms.JMSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/get/messages")
public class SystemController {


    UserReceiveMessageService serReceiveMessageService;

    @Autowired
    RabbitUserMessagingReceiveService rabbitReceiver;


    public ResponseEntity<?> getMessage() throws JMSException {
        try {
            return ResponseEntity.ok(serReceiveMessageService.receiveUser());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping("/rabbit")
    public ResponseEntity<?> getMessageFromRabbitMQ() throws JMSException {
        try {
            return ResponseEntity.ok(rabbitReceiver.receiveUser());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
