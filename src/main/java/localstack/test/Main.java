package localstack.test;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@SpringBootApplication
@EnableSqs
@RestController
public class Main {

    @Autowired
    private AmazonSQSAsync amazonSqs;

    @Autowired
    private QueueMessagingTemplate messagingTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
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

    @Bean
    public AmazonSQSAsync amazonSQS() {
        return AmazonSQSAsyncClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("http://localhost:4576", "eu-west-1")
                )
                .withCredentials(
                        new AWSStaticCredentialsProvider(new BasicAWSCredentials("test", "test"))
                )
                .build();
    }

    @Bean
    public QueueMessagingTemplate messagingTemplate(AmazonSQSAsync amazonSqs) {
        return new QueueMessagingTemplate(amazonSqs);
    }
}
