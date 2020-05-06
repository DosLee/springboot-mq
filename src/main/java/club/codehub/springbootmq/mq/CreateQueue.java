package club.codehub.springbootmq.mq;

import club.codehub.springbootmq.utils.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 描述:
 * 动态创建队列
 *
 * @author LIL on 2020-05-06 21:57
 */
@Slf4j
@Configuration
public class CreateQueue {

    @Bean("queuesNames")
    public Map<String, String> queuesNames() {
        return QueueEnum.getQueuesNames();
    }

    /**
     * 动态创建交换机
     */
    @Bean("createExchange")
    public void createExchange() {
        // 遍历交换机枚举
        ExchangeEnum.toLists().forEach(exchangeEnum -> {
            Exchange exchange;
            switch (exchangeEnum.getType()) {
                case fanout:
                    exchange = ExchangeBuilder.fanoutExchange(exchangeEnum.getExchangeName()).durable(exchangeEnum.isDurable()).build();
                    break;
                case topic:
                    exchange = ExchangeBuilder.topicExchange(exchangeEnum.getExchangeName()).durable(exchangeEnum.isDurable()).build();
                    break;
                case headers:
                    exchange = ExchangeBuilder.headersExchange(exchangeEnum.getExchangeName()).durable(exchangeEnum.isDurable()).build();
                    break;
                case direct:
                default:
                    exchange = ExchangeBuilder.directExchange(exchangeEnum.getExchangeName()).durable(exchangeEnum.isDurable()).build();
                    break;
            }
            // 将交换机注册到spring bean工厂 让spring实现交换机的管理
            Register.registerBean(exchangeEnum.toString() + "_exchange", exchange);
        });
    }

    /**
     * 动态创建队列
     */
    @Bean("CreateQueue")
    public void createQueue() {
        // 遍历队列枚举 将队列注册到spring bean工厂 让spring实现队列的管理
        QueueEnum.toLists().forEach(queueEnum -> {
            Register.registerBean(queueEnum.toString() + "_queue", new Queue(queueEnum.getQueueName(), queueEnum.isDurable(), queueEnum.isExclusive(), queueEnum.isAutoDelete(), queueEnum.getArguments()));
        });
    }

    /**
     * 动态绑定
     */
    @Bean("createBinding")
    public void createBinding() {
        // 遍历队列枚举 将队列绑定到指定交换机
        QueueEnum.toLists().forEach(queueEnum -> {
            String toString = queueEnum.toString();
            log.info("[toString: {}, queue: {}]", toString, queueEnum);
            Queue queue = SpringBeanUtil.getBean(toString + "_queue", Queue.class);
            // 声明绑定关系
            Binding binding;
            // 根据不同的交换机模式 获取不同的交换机对象（注意：刚才注册时使用的是父类Exchange，这里获取的时候将类型获取成相应的子类）生成不同的绑定规则
            switch (queueEnum.getExchangeEnum().getType()) {
                case fanout:
                    FanoutExchange fanoutExchange = SpringBeanUtil.getBean(queueEnum.getExchangeEnum().toString() + "_exchange", FanoutExchange.class);
                    binding = BindingBuilder.bind(queue).to(fanoutExchange);
                    break;
                case topic:
                    TopicExchange topicExchange = SpringBeanUtil.getBean(queueEnum.getExchangeEnum().toString() + "_exchange", TopicExchange.class);
                    binding = BindingBuilder.bind(queue).to(topicExchange).with(queueEnum.getRoutingKey());
                    break;
                case headers:
                    HeadersExchange headersExchange = SpringBeanUtil.getBean(queueEnum.getExchangeEnum().toString() + "_exchange", HeadersExchange.class);
                    if (queueEnum.isWhereAll()) {
                        // whereAll表示全部匹配
                        binding = BindingBuilder.bind(queue).to(headersExchange).whereAll(queueEnum.getHeaders()).match();
                    } else {
                        // whereAny表示部分匹配
                        binding = BindingBuilder.bind(queue).to(headersExchange).whereAny(queueEnum.getHeaders()).match();
                    }
                    break;
                case direct:
                default:
                    DirectExchange directExchange = SpringBeanUtil.getBean(queueEnum.getExchangeEnum().toString() + "_exchange", DirectExchange.class);
                    binding = BindingBuilder.bind(queue).to(directExchange).with(queueEnum.getRoutingKey());
                    break;
            }
            // 将绑定关系注册到spring bean工厂 让spring实现绑定关系的管理
            Register.registerBean(queueEnum.toString() + "_binding", binding);
        });
    }
}
