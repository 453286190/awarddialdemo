package com.example.awarddialdemo.controller;

import com.example.awarddialdemo.dto.ResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @param
 * @author: 闫沛鑫
 * @date: 2019-04-17 17:19
 * @return
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/add")
    public ResponseVO addUser(){

        return new ResponseVO();
    }
}
