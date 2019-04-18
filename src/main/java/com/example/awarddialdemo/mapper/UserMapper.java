package com.example.awarddialdemo.mapper;

import com.example.awarddialdemo.common.BaseMapper;
import com.example.awarddialdemo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
}