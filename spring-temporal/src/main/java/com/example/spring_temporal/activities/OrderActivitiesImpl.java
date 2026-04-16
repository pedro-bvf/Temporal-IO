package com.example.spring_temporal.activities;

import io.temporal.failure.ApplicationFailure;
import org.springframework.stereotype.Component;

@Component
public class OrderActivitiesImpl implements OrderActivities {
  @Override
  public void reserveStock(String orderId) {
    System.out.println("[Activity] Reserving stock: " + orderId);
  }

  @Override
  public void chargePayment(String orderId) {
    System.out.println("[Activity] Charging payment: " + orderId);

    // Falha temporaria (vai retentar):
    if (orderId.startsWith("FAIL-RETRY")) {
      throw ApplicationFailure.newFailure(
        "Temporary payment gateway failure",
        "GATEWAY_ERROR"
      );
    }

    // Falha de negocio (nao retenta se estiver em doNotRetry):
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
    System.out.println("[Activity] Sending confirmation email: " + orderId);
  }
}
