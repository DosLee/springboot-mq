package club.codehub.springbootmq.mq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 描述:
 * 监听者
 *
 * @author LIL on 2020-05-06 22:56
 */
@Component
@Slf4j
public class Listener {

    @RabbitListener(queues = {"#{queuesNames.topic_test}"})
    public void topicTest(Message message, Channel channel) {
        log.info("[用户监听到Topic消息: {}]", new String(message.getBody()));
        log.info("[Heads: {}]", message.getMessageProperties().getHeaders());
        log.info("[======================================]");
    }


}
