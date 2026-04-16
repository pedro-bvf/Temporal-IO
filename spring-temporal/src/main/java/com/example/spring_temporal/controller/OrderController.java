package com.example.spring_temporal.controller;

import com.example.spring_temporal.data.OrderResultResponseDTO;
import com.example.spring_temporal.data.StartOrderResponseDTO;
import com.example.spring_temporal.client.OrderWorkflowClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
  private final OrderWorkflowClient workflowClient;

  @PostMapping("/{orderId}")
  public ResponseEntity<StartOrderResponseDTO> startOrder(@PathVariable String orderId) {
    var startOrder = workflowClient.startWorkFlow(orderId);
    return ResponseEntity.accepted().body(startOrder);
  }

  @GetMapping("/{workflowId}")
  public ResponseEntity<OrderResultResponseDTO> getOrderResult(@PathVariable String workflowId) {
    var result = workflowClient.getOrderResultBlocking(workflowId);
    return ResponseEntity.ok(result);
  }
}
