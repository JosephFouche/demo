package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class LoanApprovalRequest {
      private Long idRequest;
    private boolean approve; // true = aprobar, false = rechazar
    private String rejectionMotive; // obligatorio si aprobar = false
}
