package com.example.assetService.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepreciationRequest {
    private Long assetId;
    private Long userId;
    private Long deptId;
    private Double valuePerDay;
}
