package com.example.awarddialdemo.controller;

import com.example.awarddialdemo.sevice.UserService;
import com.example.awarddialdemo.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    private UserService userService;

    /**
     * 新增普通用户
     * @return
     */
    @GetMapping("/add")
    public ResponseVO addUser(@RequestParam Integer userSum){
        userService.addUser(userSum);
        return new ResponseVO();
    }


}
