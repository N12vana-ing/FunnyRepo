package com.zykspring.funnycore.message.test;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Consumer {

    private Logger log = LoggerFactory.getLogger(Consumer.class);

    @KafkaListener(topics = {"sunny001"})
    public void consumer1(ConsumerRecord consumerRecord){
        Optional<Object> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        if(kafkaMessage.isPresent()){
            Object o = kafkaMessage.get();
            log.info("consumer1 message is : "+o);
        }
    }

    @KafkaListener(topics = {"sunny001"})
    public void consumer2(ConsumerRecord consumerRecord){
        Optional<Object> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        if(kafkaMessage.isPresent()){
            Object o = kafkaMessage.get();
            log.info("consumer2 message is : "+o);
        }
    }

    @KafkaListener(topics = {"sunny002"})
    public void consumer3(ConsumerRecord consumerRecord){
        Optional<Object> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        if(kafkaMessage.isPresent()){
            Object o = kafkaMessage.get();
            log.info("consumer3 message is : "+o);
        }
    }

}
