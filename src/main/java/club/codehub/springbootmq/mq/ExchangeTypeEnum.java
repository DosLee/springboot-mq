package club.codehub.springbootmq.mq;

import lombok.Getter;

/**
 * 描述:
 *
 * @author LIL on 2020-05-06 22:09
 */
@Getter
public enum ExchangeTypeEnum {
    /**
     * 广播交换机
     */
    fanout,
    /**
     * 直连交换机
     */
    direct,
    /**
     * 通配符交换机
     */
    topic,
    /**
     * 头交换机
     */
    headers
    ;

    /**
     * 交换机类型
     */
    private String exchangeType;
}
