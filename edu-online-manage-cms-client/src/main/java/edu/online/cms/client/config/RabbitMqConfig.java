package edu.online.cms.client.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public static final String QUEUE_CMS_POSTPAGE = "queue_cms_postpage";
    //交换机的名称
    public static final String EX_ROUTING_CMS_POSTPAGE = "ex_routing_cms_postpage";
    //routingKey 即站点Id
    public String routingKey = "5a751fab6abb5044e0d19ea1";

    /**
     * 交换机配置路由工作模式 使用direct类型
     *
     * @return the exchange
     */
    @Bean(EX_ROUTING_CMS_POSTPAGE)
    public Exchange exchange() {
        //durable(true)表示交换机持久化 mq重启交换机不会消失
        return ExchangeBuilder.directExchange(EX_ROUTING_CMS_POSTPAGE).durable(true).build();
    }

    //声明队列
    @Bean(QUEUE_CMS_POSTPAGE)
    public Queue queue() {
        Queue queue = new Queue(QUEUE_CMS_POSTPAGE);
        return queue;
    }

    /**
     * 绑定队列到交换机 并指定路由key
     */
    @Bean
    public Binding binding(@Qualifier(QUEUE_CMS_POSTPAGE) Queue queue,
                           @Qualifier(EX_ROUTING_CMS_POSTPAGE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
    }
}
