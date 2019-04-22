package com.example.awarddialdemo.sevice;

import com.example.awarddialdemo.dto.AwardAddInfo;
import com.example.awarddialdemo.exception.MessageException;

/**
 * @param
 * @author: 闫沛鑫
 * @date: 2019-04-18 17:04
 * @return
 */
public interface AwardService {

    void addAward(AwardAddInfo awardAddInfo) throws MessageException;
    void sendAward();
}
