package com.example.spring_temporal.controller;

import com.example.spring_temporal.data.OrderResultResponse;
import com.example.spring_temporal.data.StartOrderResponse;
import com.example.spring_temporal.service.OrderWorkflowService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
  private final OrderWorkflowService service;

  @PostMapping("/{orderId}")
  public ResponseEntity<StartOrderResponse> start(@PathVariable String orderId) {
    var startOrder = service.startOrder(orderId);
    return ResponseEntity.ok(startOrder);
  }

  @GetMapping("/{workflowId}")
  public ResponseEntity<OrderResultResponse> result(@PathVariable String workflowId) {
    var result = service.getResultBlocking(workflowId);
    return ResponseEntity.ok(result);
  }
}
