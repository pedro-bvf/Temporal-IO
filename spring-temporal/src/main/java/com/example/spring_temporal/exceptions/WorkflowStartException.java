package com.example.spring_temporal.exceptions;

import lombok.Getter;

@Getter
public class WorkflowStartException extends RuntimeException {
  private final String orderId;

  public WorkflowStartException(String orderId, Exception ex) {
    super("Não foi possivel iniciar o workflow", ex);
    this.orderId = orderId;
  }
}
