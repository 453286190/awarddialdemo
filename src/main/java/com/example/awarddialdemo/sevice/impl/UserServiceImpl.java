package com.example.awarddialdemo.sevice.impl;

import com.example.awarddialdemo.entity.User;
import com.example.awarddialdemo.mapper.UserMapper;
import com.example.awarddialdemo.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * @param
 * @author: 闫沛鑫
 * @date: 2019-04-18 10:08
 * @return
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public void addUser() {
        List<User> userList = new LinkedList<>();
        for(int i = 0;i < 500;i++){
            User user = new User();
            user.setUsername("用户" + (i + 1));
            userList.add(user);
        }

        userMapper.insertList(userList);
    }


    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.addUser();
    }
}
