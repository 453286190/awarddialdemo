package com.example.awarddialdemo.exception;

import lombok.Data;

/**
 * @param
 * @author: 闫沛鑫
 * @date: 2019-04-22 14:08
 * @return
 */

public class MessageException extends BaseException  {
    private static final long serialVersionUID = 925878776574686097L;

    public MessageException(String message){
        super(4001,message);
    }
}
