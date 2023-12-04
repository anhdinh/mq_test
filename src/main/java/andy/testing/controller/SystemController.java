package andy.testing.controller;

import andy.testing.service.mq.receive.UserReceiveMessageService;
import jakarta.jms.JMSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/get/messages")
public class SystemController {

    @Autowired
    UserReceiveMessageService serReceiveMessageService;

    @GetMapping
    public ResponseEntity<?> getMessage() throws JMSException {
        try {
            return ResponseEntity.ok(serReceiveMessageService.receiveOrder());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }


    }

}
