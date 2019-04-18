package com.example.awarddialdemo.common;

import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @param
 * @author: 闫沛鑫
 * @date: 2019-04-18 11:50
 * @return
 */

public interface BaseMapper<T> extends Mapper<T>,MySqlMapper<T> {
}
