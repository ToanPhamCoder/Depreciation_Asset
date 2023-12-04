package com.example.assetService.service.Impl;

import com.example.assetService.model.UpdateHistory;
import com.example.assetService.repository.UpdateHistoryRepository;
import com.example.assetService.service.UpdateHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class UpdateHistoryServiceImpl implements UpdateHistoryService {
    private final UpdateHistoryRepository updateHistoryRepository;
    @Override
    public List<UpdateHistory> getListUpdateHistoryByAssetId(Long assetId) {
        return updateHistoryRepository.getHistoryUpdateById(assetId);
    }
    @Override
    public List<UpdateHistory> getListReduceHistoryByAssetId(Long assetId){
        return updateHistoryRepository.getHistoryReduceById(assetId);
    }
}