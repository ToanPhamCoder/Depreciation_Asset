package com.example.userService.controller;

import com.example.userService.dto.response.Response;
import com.example.userService.model.Department;
import com.example.userService.model.User;
import com.example.userService.service.DepartmentService;
import com.example.userService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Response> getAllUser(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page,size);
        Map<String,Object> data = new HashMap<>();
        data.put("listUser",userService.findAllUser(pageable));
        Response response = new Response("Danh sách người dùng",data);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getUserById(@PathVariable long id){
        Map<String,Object> data = new HashMap<>();
        data.put("user",userService.findUserById(id));
        Response response = new Response("Thông tin người dùng",data);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/v1/{id}")
    public User getUserByIdv1(@PathVariable long id){
        return userService.findUserById(id);
    }


}
