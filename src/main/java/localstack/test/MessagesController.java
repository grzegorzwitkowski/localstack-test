package localstack.test;

import com.amazonaws.services.sqs.AmazonSQS;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class MessagesController {

    private final AmazonSQS amazonSqs;
    private final QueueMessagingTemplate messagingTemplate;

    public MessagesController(AmazonSQS amazonSqs, QueueMessagingTemplate messagingTemplate) {
        this.amazonSqs = amazonSqs;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("test-queue")
    public void onMessage(TestMessage testMessage) {
        System.out.println("test-queue received a new message: " + testMessage);
    }

    @RequestMapping("/list-queues")
    public List<String> listQueues() {
        return amazonSqs.listQueues().getQueueUrls();
    }

    @RequestMapping(path = "/messages", method = POST)
    public void sendMessage(@RequestBody TestMessage testMessage) {
        messagingTemplate.convertAndSend("test-queue", testMessage);
    }
}
