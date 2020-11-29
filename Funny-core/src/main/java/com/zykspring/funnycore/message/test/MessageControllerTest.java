package com.zykspring.funnycore.message.test;

import com.zykspring.funnycore.base.BaseController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "测试消息Rest")
@RequestMapping("/sys/message")
@RestController
public class MessageControllerTest extends BaseController {

    @Autowired
    private Producer producer;

    @PostMapping("/send/{topic}/{message}")
    public void queryAll(@PathVariable String topic,@PathVariable String message){

        System.out.println(" ---- producer ---- ");
        producer.send(topic,message);

    }

}
