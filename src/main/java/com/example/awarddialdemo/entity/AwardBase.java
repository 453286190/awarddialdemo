package com.example.awarddialdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @param
 * @author: 闫沛鑫
 * @date: 2019-04-17 16:44
 * @return
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "award_base")
public class AwardBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //   奖品类型
    @Column(name = "award_type")
    private String awardType;
    //   中奖概率
    @Column(name = "award_odd")
    private Double awardOdd;

}
