package edu.online.manage.media.client.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname RabbitConfig
 * @Description 配置交换机和队列以及它们的绑定
 * @Date 2020/2/17 19:56
 * @Created by zhoutao
 */
@Configuration
public class RabbitMqConfig {
    //队列的名称
    public static final String QUEUE_MEDIA_PROCESSTASK = "queue_media_processor";
    //交换机的名称
    public static final String EX_MEDIA_PROCESSTASK = "ex_media_processor";
    //视频处理路由
    @Value("${edu.mq.routingkey-media-video}")
    public  String routingkey_media_video;

    /**
     * 交换机配置路由工作模式 使用direct类型
     *
     * @return the exchange
     */
    @Bean(EX_MEDIA_PROCESSTASK)
    public Exchange exchange() {
        //durable(true)表示交换机持久化 mq重启交换机不会消失
        return ExchangeBuilder.directExchange(EX_MEDIA_PROCESSTASK).durable(true).build();
    }

    //声明队列
    @Bean(QUEUE_MEDIA_PROCESSTASK)
    public Queue queue() {
        Queue queue = new Queue(QUEUE_MEDIA_PROCESSTASK);
        return queue;
    }

    /**
     * 绑定队列到交换机 并指定路由key
     */
    @Bean
    public Binding binding(@Qualifier(QUEUE_MEDIA_PROCESSTASK) Queue queue,
                           @Qualifier(EX_MEDIA_PROCESSTASK) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingkey_media_video).noargs();
    }
}
