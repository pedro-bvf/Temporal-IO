package com.example.spring_temporal.activities;

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

    // Falha temporária (vai retentar):
    // throw ApplicationFailure.newFailure("Falha temporária no gateway", "GATEWAY_ERROR");

    // Falha de negócio (não retenta se estiver em doNotRetry):
    // throw ApplicationFailure.newNonRetryableFailure("Pagamento recusado", "PAYMENT_DECLINED");
  }

  @Override
  public void sendConfirmationEmail(String orderId) {
    System.out.println("[Activity] Email de confirmação: " + orderId);
  }
}
