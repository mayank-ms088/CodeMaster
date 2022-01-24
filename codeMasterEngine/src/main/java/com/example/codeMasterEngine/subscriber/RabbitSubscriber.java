package com.example.codeMasterEngine.subscriber;


import com.example.codeMasterEngine.config.MQConfig;
import com.example.codeMasterEngine.dto.CodeExecutionRequest;

import com.example.codeMasterEngine.dto.CodeExecutionResponse;
import com.example.codeMasterEngine.worker.CodeExecutionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class RabbitSubscriber {

    @Autowired
    private CodeExecutionService codeExecutionService;

    @RabbitListener(queues = MQConfig.QUEUE)
    public void listener(CodeExecutionRequest codeExecutionRequest) throws IOException, InterruptedException {
        System.out.println(codeExecutionRequest);
        CodeExecutionResponse response = codeExecutionService.execute(codeExecutionRequest);
    }
}
