package com.zykspring.funnycore.message.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void send(String topic,String message){
        for(int i = 0;i < 10;i++){
            kafkaTemplate.send(topic,message+"-"+i);
            kafkaTemplate.send("sunny002",message+"-"+i);
        }
    }

}
