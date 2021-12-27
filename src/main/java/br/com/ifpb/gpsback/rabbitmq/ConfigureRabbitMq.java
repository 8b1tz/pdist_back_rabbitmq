package br.com.ifpb.gpsback.rabbitmq;

import br.com.ifpb.gpsback.rabbitmq.consumer.TaskHandler;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigureRabbitMq {

    public static final String EXCHANGE_NAME = "taskexchange";
    public static final String QUEUE_NAME = "tasksqueue";

    @Bean
    Queue createQueue() {
        return new Queue(QUEUE_NAME, false, false, false);
    }

    @Bean
    TopicExchange exchange(){
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding binding(Queue q, TopicExchange exchange){
        return BindingBuilder.bind(q).to(exchange).with("pdist.#");
    }

    @Bean
    SimpleMessageListenerContainer container( ConnectionFactory connectionFactory
            , MessageListenerAdapter messageListenerAdapter){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(messageListenerAdapter);
        return container;
    }


    @Bean
    MessageListenerAdapter listenerAdapter(TaskHandler handler){
        return new MessageListenerAdapter(handler, "handleMessage");
    }


}
