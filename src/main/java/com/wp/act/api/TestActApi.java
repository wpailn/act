package com.wp.act.api;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/test")
@Slf4j
public class TestActApi {
    @GetMapping(path = "/processInfo/{key}")
    public void processInfo(@PathVariable String key){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> list = processDefinitionQuery.processDefinitionKey(key).
                orderByProcessDefinitionVersion().desc().list();
        list.forEach(processDefinition -> {
                    System.out.println("流程定义ID："+processDefinition.getId());
                    System.out.println("流程定义的名称："+processDefinition.getName());
                    System.out.println("流程定义的Key："+processDefinition.getKey());
                    System.out.println("流程定义的版本号："+processDefinition.getVersion());
                }
        );
    }
}
