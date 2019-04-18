package com.example.awarddialdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

/**
 * @param
 * @author: 闫沛鑫
 * @date: 2019-04-18 10:32
 * @return
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_award")
public class UserAward {
    private Long id;
    private Long userId;
    private Long AwardId;
//    奖品秘钥
    private String AwardKey;
}
