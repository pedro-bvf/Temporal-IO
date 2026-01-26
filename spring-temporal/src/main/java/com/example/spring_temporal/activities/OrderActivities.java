package com.example.spring_temporal.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface OrderActivities {
  @ActivityMethod
  void reserveStock(String orderId);
  @ActivityMethod
  void chargePayment(String orderId);
  @ActivityMethod
  void sendConfirmationEmail(String orderId);
}
