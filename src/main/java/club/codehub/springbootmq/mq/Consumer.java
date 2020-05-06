package club.codehub.springbootmq.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 描述:
 * 消费者
 *
 * @author LIL on 2020-05-06 23:07
 */
@RestController
@RequestMapping("/rabbitMq")
@Slf4j
public class Consumer {

    private final AmqpTemplate amqpTemplate;

    public Consumer(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    /**
     * 实现消息的发送
     */
    @RequestMapping(value = "/helloQueue", method = RequestMethod.GET)
    public String send() {
        log.info("[开始发送消息]");
        String message = "this is queue test!";
        //如果这里只是发送一次，只会有一个消费者能够消费到此消息，多条消息是均摊的
        for (int i = 0; i < 10; i++) {
            log.info("[信息: {}]", i);
            this.amqpTemplate.convertAndSend(QueueEnum.topic_test.getQueueName(), message);
        }
        log.info("[结束发送消息]");
        return "ok";
    }
}
