package com.baidu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/{id}")
    public String getUserById(@PathVariable("id") int id) {
        return "user id is:" + id;
    }
}
