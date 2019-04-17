package com.example.awarddialdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class JoinAwardPeople {
    private Integer peopleId;
    private String username;
    private String awardId;
}
