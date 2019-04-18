package com.example.awarddialdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 奖品
 * @param
 * @author: 闫沛鑫
 * @date: 2019-04-17 14:51
 * @return
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "award")
public class Award {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//   奖品秘钥
    @Column(name = "award_key")
    private String awardKey;
//    是否已发出
    @Column(name = "is_send")
    private Boolean isSend;


}
