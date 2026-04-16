package com.example.spring_temporal.workflow;

import com.example.spring_temporal.activities.OrderActivities;
import com.example.spring_temporal.data.Order;
import com.example.spring_temporal.defaults.ActivityOptionsDefaults;
import io.temporal.workflow.Workflow;

public class OrderWorkflowImpl implements OrderWorkflow {

  private final OrderActivities activities =
    Workflow.newActivityStub(
      OrderActivities.class,
      ActivityOptionsDefaults.defaultOptions()
    );

  @Override
  public Order processOrder(String orderId) {
    // Workflow is deterministic: there's no direct I/O here.
    activities.reserveStock(orderId);
    activities.chargePayment(orderId);
    activities.sendConfirmationEmail(orderId);

    return new Order(orderId, "CONFIRMED");
  }
}
