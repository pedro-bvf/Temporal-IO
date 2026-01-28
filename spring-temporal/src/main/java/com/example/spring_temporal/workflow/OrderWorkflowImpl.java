package com.example.spring_temporal.workflow;

import com.example.spring_temporal.activities.OrderActivities;
import com.example.spring_temporal.data.Order;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class OrderWorkflowImpl implements OrderWorkflow {
  private final RetryOptions retryOptions = RetryOptions.newBuilder()
    .setInitialInterval(Duration.ofSeconds(1))
    .setBackoffCoefficient(2.0)
    .setMaximumInterval(Duration.ofSeconds(30))
    .setMaximumAttempts(5)
    .build();

  private final ActivityOptions activityOptions = ActivityOptions.newBuilder()
    .setStartToCloseTimeout(Duration.ofSeconds(10))
    .setRetryOptions(retryOptions)
    .build();

  private final OrderActivities activities =
    Workflow.newActivityStub(OrderActivities.class, activityOptions);

  @Override
  public Order processOrder(String orderId) {
    // Workflow é determinístico: sem I/O direto aqui.
    activities.reserveStock(orderId);
    activities.chargePayment(orderId);
    activities.sendConfirmationEmail(orderId);

    return new Order(orderId, "CONFIRMED");
  }
}
