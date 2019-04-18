package com.example.awarddialdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**
 * 注意项：
 * 1、通用mapper与普通mapper不要放在一起
 * 2、必须要配置MapperScannerConfigurer扫描，
 * 指定普通mapper的包以及将通用mapper的路径设置到MapperScannerConfigurer的属性当中，全路径，多个用逗号分隔
 * @param
 * @author: 闫沛鑫
 * @date: 2019-04-18 16:37
 * @return
 */

@Component
//跨域
@CrossOrigin
public class MybatisConfig {

    /**
     * 配置通用Mapper
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.example.awarddialdemo.mapper");
        Properties properties = new Properties();
        properties.setProperty("mappers","com.example.awarddialdemo.common.BaseMapper");
        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }
}
