package com.example.awarddialdemo.controller;

import com.example.awarddialdemo.dto.AwardAddInfo;
import com.example.awarddialdemo.entity.Award;
import com.example.awarddialdemo.exception.MessageException;
import com.example.awarddialdemo.sevice.AwardService;
import com.example.awarddialdemo.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 新增奖品
     * @param awardAddInfo
     * @return
     */
    @GetMapping("/add")
    public ResponseVO addAwardLevel(@RequestBody AwardAddInfo awardAddInfo) throws MessageException {
        awardService.addAward(awardAddInfo);
        return new ResponseVO();
    }

    /**
     * 抽奖
     * 默认参与用户为所有用户，奖品为所有奖品
     * @return
     */
    @GetMapping("/send")
    public ResponseVO sendAward() throws MessageException {
        awardService.sendAward();
        return new ResponseVO();
    }

    /**
     * 初始化奖品
     * @return
     */
    @GetMapping("/clear")
    public ResponseVO clearAward(){
        awardService.clearAward();
        return new ResponseVO();
    }
}
