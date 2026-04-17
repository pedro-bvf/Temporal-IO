package com.example.spring_temporal.activities;

import io.temporal.activity.Activity;
import io.temporal.failure.ApplicationFailure;
import org.springframework.stereotype.Component;

@Component
public class OrderActivitiesImpl implements OrderActivities {
  @Override
  public void reserveStock(String orderId) {
    //REST call to stock service to reserve items (simulated here with a print statement)
    int attempt = Activity.getExecutionContext().getInfo().getAttempt();
    System.out.println("[Activity] Reserving stock (attempt " + attempt + "): " + orderId);
  }

  @Override
  public void chargePayment(String orderId) {
    //REST call to payment gateway to charge the customer (simulated here with a print statement)
    int attempt = Activity.getExecutionContext().getInfo().getAttempt();
    System.out.println("[Activity] Charging payment (attempt " + attempt + "): " + orderId);

    // Temporary failure (will retry):
    if (orderId.startsWith("FAIL-RETRY")) {
      throw ApplicationFailure.newFailure(
        "Temporary payment gateway failure",
        "GATEWAY_ERROR"
      );
    }

    // Business failure (do not retry if in FAIL-NORETRY):
    if (orderId.startsWith("FAIL-NORETRY")) {
      throw ApplicationFailure.newNonRetryableFailure(
        "Payment declined",
        "PAYMENT_DECLINED"
      );
    }

    System.out.println("[Activity] Payment approved: " + orderId);
  }

  @Override
  public void sendConfirmationEmail(String orderId) {
    //Kafka message to email service to send confirmation email (simulated here with a print statement)
    int attempt = Activity.getExecutionContext().getInfo().getAttempt();
    System.out.println("[Activity] Sending confirmation email (attempt " + attempt + "): " + orderId);
  }
}
