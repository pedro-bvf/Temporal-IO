package com.example.spring_temporal.service;

import com.example.spring_temporal.data.OrderResultResponse;
import com.example.spring_temporal.data.StartOrderResponse;
import com.example.spring_temporal.exceptions.WorkflowNotCompletedException;
import com.example.spring_temporal.workflow.OrderWorkflow;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class OrderWorkflowService {

  private final WorkflowClient client;

  public StartOrderResponse startOrder(String orderId) {
    String workflowId = "order-" + orderId;

    OrderWorkflow workflow = client.newWorkflowStub(
      OrderWorkflow.class,
      WorkflowOptions.newBuilder()
        .setTaskQueue("TEMPORAL_TASK_QUEUE")
        .setWorkflowId(workflowId)
        .build()
    );

    WorkflowExecution exec = WorkflowClient.start(workflow::processOrder, orderId);

    return new StartOrderResponse(workflowId, exec.getRunId());
  }

  public OrderResultResponse getResultBlocking(String workflowId) {
    try {
      WorkflowStub stub = client.newUntypedWorkflowStub(workflowId);
      String result = stub.getResult(String.class); // bloqueia até terminar
      return new OrderResultResponse(workflowId, result);
    } catch (Exception ex) {
      throw new WorkflowNotCompletedException(workflowId, ex);
    }
  }
}
