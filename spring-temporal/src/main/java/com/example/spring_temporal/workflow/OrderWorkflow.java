package com.example.spring_temporal.workflow;

import com.example.spring_temporal.data.OrderDTO;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface OrderWorkflow {
  @WorkflowMethod
  OrderDTO processOrder(String orderId);
}
