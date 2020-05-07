package club.codehub.springbootmq.mq.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 描述:
 *
 * @author LIL on 2020-05-06 22:05
 */
@Getter
@AllArgsConstructor
public enum ExchangeEnum {

    mt_topic("ec.message.mt", ExchangeTypeEnum.topic, true);

    /**
     * 交换机名称
     */
    String exchangeName;

    /**
     * 交换机类型
     */
    ExchangeTypeEnum type;

    /**
     * 是否为持久交换机
     */
    boolean durable;

    public static List<ExchangeEnum> toLists() {
        return Stream.of(values()).collect(Collectors.toList());
    }
}
