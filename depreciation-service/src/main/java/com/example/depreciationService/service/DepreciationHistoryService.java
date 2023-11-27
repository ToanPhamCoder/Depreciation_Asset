package com.example.depreciationService.service;

import com.example.depreciationService.model.Depreciation;
import com.example.depreciationService.model.DepreciationHistory;

import java.util.Date;
import java.util.List;

public interface DepreciationHistoryService {

    boolean saveDepreciationHistory(DepreciationHistory depreciationHistory);

    List<DepreciationHistory> findByDepreciation(Depreciation depreciation);
    List<Object> getDepreciationValue(Date date);
    DepreciationHistory getDepreciationByDate(Date date);
}