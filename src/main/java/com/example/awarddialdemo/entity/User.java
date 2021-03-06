package com.example.awarddialdemo.entity;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

/**
 * 参加抽奖人
 * @param
 * @author: 闫沛鑫
 * @date: 2019-04-17 15:03
 * @return
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {
    private Long id;
    private String username;

}
