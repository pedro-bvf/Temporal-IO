package com.example.spring_temporal.activities;

import io.temporal.failure.ApplicationFailure;
import org.springframework.stereotype.Component;

@Component
public class OrderActivitiesImpl implements OrderActivities {
  @Override
  public void reserveStock(String orderId) {
    System.out.println("[Activity] Reservando stock: " + orderId);
  }

  @Override
  public void chargePayment(String orderId) {
    System.out.println("[Activity] Cobrando pagamento: " + orderId);

    // Falha temporaria (vai retentar):
    if (orderId.startsWith("FAIL-RETRY")) {
      throw ApplicationFailure.newFailure("Falha temporária no gateway", "GATEWAY_ERROR");
    }

    // Falha de negocio (nao retenta se estiver em doNotRetry):
    if (orderId.startsWith("FAIL-NORETRY")) {
      throw ApplicationFailure.newNonRetryableFailure("Pagamento recusado", "PAYMENT_DECLINED");
    }

    System.out.println("[Activity] Pagamento aprovado: " + orderId);
  }

  @Override
  public void sendConfirmationEmail(String orderId) {
    System.out.println("[Activity] Email de confirmação: " + orderId);
  }
}
