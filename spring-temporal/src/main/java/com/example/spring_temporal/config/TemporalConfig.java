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

  /**
   * Task queue used by the Order workflow and its activities.
   */
  public static final String TASK_QUEUE = "TEMPORAL_TASK_QUEUE";

  /**
   * Creates the connection with the local Temporal service.
   *
   * @return Temporal service stubs
   */
  @Bean
  public WorkflowServiceStubs workflowServiceStubs() {
    return WorkflowServiceStubs.newLocalServiceStubs();
  }

  /**
   * Creates the Temporal client used to start and query workflows.
   *
   * @param service Temporal service connection
   * @return Workflow client instance
   */
  @Bean
  public WorkflowClient workflowClient(WorkflowServiceStubs service) {
    return WorkflowClient.newInstance(service);
  }

  /**
   * Creates the worker factory responsible for managing Temporal workers.
   *
   * @param client Temporal workflow client
   * @return WorkerFactory instance
   */
  @Bean
  public WorkerFactory workerFactory(WorkflowClient client) {
    return WorkerFactory.newInstance(client);
  }

  /**
   * Creates a worker bound to the configured task queue and registers:
   * - the workflow implementation
   * - the activity implementations
   *
   * @param factory Temporal worker factory
   * @param orderActivities Activity implementation bean
   * @return Configured worker instance
   */
  @Bean
  public Worker worker(WorkerFactory factory, OrderActivities orderActivities) {
    Worker worker = factory.newWorker(TASK_QUEUE);
    worker.registerWorkflowImplementationTypes(OrderWorkflowImpl.class);
    worker.registerActivitiesImplementations(orderActivities);
    return worker;
  }

  /**
   * Starts the Temporal workers when the Spring application boots.
   *
   * @param factory Temporal worker factory
   * @return CommandLineRunner that starts the workers
   */
  @Bean
  public CommandLineRunner startTemporalWorkers(WorkerFactory factory) {
    return args -> {
      factory.start();
      System.out.println("Temporal WorkerFactory -> Initialized");
    };
  }

}
