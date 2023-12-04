package com.example.depreciationService.controller;

import com.example.depreciationService.dto.request.DepreciationByDeptRequest;
import com.example.depreciationService.mapping.DepreciationHistoryMapping;
import com.example.depreciationService.service.DepreciationHistoryService;
import com.example.depreciationService.tasks.DepreciationHistoryTask;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/depreciation/history")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class DepreciationHistoryController {
    private final DepreciationHistoryService depreciationHistoryService;
    private final DepreciationHistoryMapping depreciationHistoryMapping;
    private final DepreciationHistoryTask depreciationHistoryTask;
    //Tất cả thông tin khấu hao của phòng ban
    @GetMapping("/dept")
    public ResponseEntity getDepreciationValueAllDept(@RequestParam int year, @RequestBody DepreciationByDeptRequest depreciation){
        List<Object> records = new ArrayList<>();
        if(depreciation.getDeptIds().size()==0)
            records = depreciationHistoryService.getDepreciationByAllDept(year);
        else
            records = depreciationHistoryService.getDepreciationByDeptIds(year,depreciation.getDeptIds());
        return new ResponseEntity(depreciationHistoryMapping.getDepreciationDeptResponse(records), HttpStatus.OK);
    }
    @GetMapping("/test")
    public ResponseEntity getTest(@RequestParam String text) throws ParseException {
        depreciationHistoryTask.calculateDepreciationPerMonthTest(text);
        return new ResponseEntity("aa",HttpStatus.OK);
    }
}