package com.example.spring_temporal.config;

import com.example.spring_temporal.activities.OrderActivities;
import com.example.spring_temporal.workflow.OrderWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemporalConfig {

  public static final String TASK_QUEUE = "TEMPORAL_TASK_QUEUE";

  @Bean
  public WorkflowServiceStubs workflowServiceStubs() {
    return WorkflowServiceStubs.newLocalServiceStubs();
  }

  @Bean
  public WorkflowClient workflowClient(WorkflowServiceStubs service) {
    return WorkflowClient.newInstance(service);
  }

  @Bean
  public WorkerFactory workerFactory(WorkflowClient client) {
    return WorkerFactory.newInstance(client);
  }

  @Bean
  public Worker worker(WorkerFactory factory, OrderActivities orderActivities) {
    Worker worker = factory.newWorker(TASK_QUEUE);
    worker.registerWorkflowImplementationTypes(OrderWorkflowImpl.class);
    worker.registerActivitiesImplementations(orderActivities);
    return worker;
  }

  @Bean
  public CommandLineRunner startTemporalWorkers(WorkerFactory factory) {
    return args -> {
      factory.start();
      System.out.println("Temporal WorkerFactory iniciado");
    };
  }

}
