package com.example.awarddialdemo.mapper;

import com.example.awarddialdemo.common.MyBaseMapper;
import com.example.awarddialdemo.entity.UserAward;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserAwardMapper extends MyBaseMapper<UserAward> {
}