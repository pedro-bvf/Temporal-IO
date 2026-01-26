package com.example.spring_temporal.exceptions;

import lombok.Getter;

@Getter
public class WorkflowNotCompletedException extends RuntimeException {
  private final String workflowId;

  public WorkflowNotCompletedException(String workflowId, Exception ex) {
    super("Workflow ainda não concluído ou falhou", ex);
    this.workflowId = workflowId;
  }

}
