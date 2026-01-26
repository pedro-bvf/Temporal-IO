package com.example.spring_temporal.data;

public record StartOrderResponse(
  String workflowId, String runId
) {
}
