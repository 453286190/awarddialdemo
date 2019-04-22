package com.example.awarddialdemo.dto;

import lombok.Data;

import java.util.List;

/**
 * 添加奖品的提交信息
 * @param
 * @author: 闫沛鑫
 * @date: 2019-04-22 13:56
 * @return
 */
@Data
public class AwardAddInfo {
//    奖品等级总数
    private Integer awardLevelSum;
//    每个等级的奖品数量
    private List<AwardDTO> awardDTOList;

}
