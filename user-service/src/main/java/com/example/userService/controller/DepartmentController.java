package com.example.userService.controller;

import com.example.userService.dto.response.Response;
import com.example.userService.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user/department")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;
    @GetMapping
    public ResponseEntity<Response> getAllDepartment() {
        Map<String,Object> data = new HashMap<>();
        data.put("listDepartment",departmentService.findAllDepartment());
        Response response = new Response("Danh sách phòng ban", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response> getDepartmentById(@PathVariable Long id){
        Map<String, Object> data = new HashMap<>();
        data.put("dept",departmentService.findDepartmentById(id));
        return new ResponseEntity<>(new Response("Thông tin phòng ban",data),HttpStatus.OK);
    }
}
