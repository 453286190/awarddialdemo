package com.example.awarddialdemo.controller;

import com.example.awarddialdemo.entity.Award;
import com.example.awarddialdemo.sevice.AwardService;
import com.example.awarddialdemo.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @param
 * @author: 闫沛鑫
 * @date: 2019-04-18 16:51
 * @return
 */
@RestController
@RequestMapping("/award")
public class AwardController {

    @Autowired
    private AwardService awardService;

    /**
     * 新增奖品,默认几等奖就发几份,中奖比率改库
     * @param awardLevelSum 一共有几等奖品
     * @return
     */
    @GetMapping("/add")
    public ResponseVO addAward(@RequestParam String awardLevelSum){
        awardService.addAward(awardLevelSum);
        return new ResponseVO();
    }
}
