package com.wp.act;

import com.wp.act.common.act.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.process.runtime.connector.Connector;
import org.activiti.api.process.runtime.events.ProcessCompletedEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTimeUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Random;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class ActApplication implements CommandLineRunner {

    @Resource
    private ProcessRuntime processRuntime;

    @Resource
    private SecurityUtil securityUtil;

    public static void main(String[] args) {
        SpringApplication.run(ActApplication.class, args);
    }

    @Override
    public void run(String... args) {
        securityUtil.logInAs("system");

        Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));
        log.info("> Available Process definitions: " + processDefinitionPage.getTotalItems());
        for (ProcessDefinition pd : processDefinitionPage.getContent()) {
            log.info("\t > Process definition: " + pd);
        }
    }

    /*@Scheduled(initialDelay = 1000, fixedDelay = 1000)
    public void processText() {

        securityUtil.logInAs("system");

        String content = pickRandomString();


        log.info("> Processing content: " + content + " at " + DateFormatUtils.format(new Date(),"dd-MM-yy HH:mm:ss"));

        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey("categorizeProcess")
                .withName("Processing Content: " + content)
                .withVariable("content", content)
                .build());
        log.info(">>> Created Process Instance: " + processInstance);


    }

    @Bean
    public Connector processTextConnector() {
        return integrationContext -> {
            Map<String, Object> inBoundVariables = integrationContext.getInBoundVariables();
            String contentToProcess = (String) inBoundVariables.get("content");
            if (contentToProcess.contains("activiti")) {
                log.info("> Approving content: " + contentToProcess);
                integrationContext.addOutBoundVariable("approved",
                        true);
            } else {
                log.info("> Discarding content: " + contentToProcess);
                integrationContext.addOutBoundVariable("approved",
                        false);
            }
            return integrationContext;
        };
    }

    @Bean
    public Connector tagTextConnector() {
        return integrationContext -> {
            String contentToTag = (String) integrationContext.getInBoundVariables().get("content");
            contentToTag += " :) ";
            integrationContext.addOutBoundVariable("content",
                    contentToTag);
            log.info("Final Content: " + contentToTag);
            return integrationContext;
        };
    }

    @Bean
    public Connector discardTextConnector() {
        return integrationContext -> {
            String contentToDiscard = (String) integrationContext.getInBoundVariables().get("content");
            contentToDiscard += " :( ";
            integrationContext.addOutBoundVariable("content",
                    contentToDiscard);
            log.info("Final Content: " + contentToDiscard);
            return integrationContext;
        };
    }

    @Bean
    public ProcessRuntimeEventListener<ProcessCompletedEvent> processCompletedListener() {
        return processCompleted -> log.info(">>> Process Completed: '"
                + processCompleted.getEntity().getName() +
                "' We can send a notification to the initiator: " + processCompleted.getEntity().getInitiator());
    }

    private String pickRandomString() {
        String[] texts = {"hello from london", "Hi there from activiti!", "all good news over here.", "I've tweeted about activiti today.",
                "other boring projects.", "activiti cloud - Cloud Native Java BPM"};
        return texts[new Random().nextInt(texts.length)];
    }*/
}
