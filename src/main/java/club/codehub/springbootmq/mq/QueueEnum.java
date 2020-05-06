package club.codehub.springbootmq.mq;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 描述:
 *
 * @author LIL on 2020-05-06 21:58
 */
@Getter
@AllArgsConstructor
public enum QueueEnum {

    topic_test("club.codehub.mt", "topic.test.*", true, true, false, null, false, null, ExchangeEnum.mt_topic);

    /**
     * 唯一队列名称
     */
    String queueName;

    /**
     * 路由键
     */
    String routingKey;

    /**
     * 是否为持久队列
     */
    boolean durable;

    /**
     * 是否为排他队列
     */
    boolean exclusive;

    /**
     * 如果队列为空是否删除
     */
    boolean autoDelete;

    /**
     * 队列参数
     */
    Map<String, Object> arguments;

    /**
     * 是否需要全部匹配
     */
    boolean whereAll;

    /**
     * 匹配消息头
     */
    Map<String, Object> headers;

    /**
     * 交换机
     */
    ExchangeEnum exchangeEnum;

    public static Map<String, String> getQueuesNames() {
        return Arrays.stream(QueueEnum.values()).collect(Collectors.toMap(Enum::toString, QueueEnum::getQueueName));
    }

    public static List<QueueEnum> toLists() {
        return Stream.of(values()).collect(Collectors.toList());
    }
}
