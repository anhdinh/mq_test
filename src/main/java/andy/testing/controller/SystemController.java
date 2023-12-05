package andy.testing.controller;

import andy.testing.service.mq.IUserMessageReceivingService;
import jakarta.jms.JMSException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/get/messages")
public class SystemController {

    public final IUserMessageReceivingService receiveMessagingService;

    public SystemController(IUserMessageReceivingService serReceiveMessageService) {
        this.receiveMessagingService = serReceiveMessageService;
    }

    @GetMapping("")
    public ResponseEntity<?> getMessageFromRabbitMQ() throws JMSException {
        try {
            return ResponseEntity.ok(receiveMessagingService.receiveUser());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
