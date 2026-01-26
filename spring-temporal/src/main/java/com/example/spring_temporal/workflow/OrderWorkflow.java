package com.example.spring_temporal.workflow;

import com.example.spring_temporal.data.OrderResult;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface OrderWorkflow {
  @WorkflowMethod
  OrderResult processOrder(String orderId);
}
