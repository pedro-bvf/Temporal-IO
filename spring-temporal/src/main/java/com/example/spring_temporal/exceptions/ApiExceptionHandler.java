package com.example.spring_temporal.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(WorkflowStartException.class)
  public ResponseEntity<Map<String, String>> handleWorkflowStartException(
    WorkflowStartException ex
  ) {
    return ResponseEntity.badRequest().body(Map.of(
      "orderId", ex.getOrderId(),
      "message", ex.getMessage(),
      "error", ex.getCause().getClass().getSimpleName()
    ));
  }

  @ExceptionHandler(WorkflowNotCompletedException.class)
  public ResponseEntity<Map<String, String>> handleWorkflowNotCompleted(
    WorkflowNotCompletedException ex
  ) {
    return ResponseEntity.status(409).body(Map.of(
      "workflowId", ex.getWorkflowId(),
      "message", ex.getMessage(),
      "error", ex.getCause().getClass().getSimpleName()
    ));
  }
}
