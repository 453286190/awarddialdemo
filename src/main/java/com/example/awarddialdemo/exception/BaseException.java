package com.example.awarddialdemo.exception;

import lombok.Data;

/**
 * @param
 * @author: 闫沛鑫
 * @date: 2019-04-22 14:15
 * @return
 */
@Data
public abstract class BaseException extends Exception {
    private static final long serialVersionUID = 709182668552911550L;

    private final Integer RETURN_CODE;
    private final String RETURN_MESSAGE;

    public BaseException(Integer RETURN_CODE, String RETURN_MESSAGE){
        super(RETURN_MESSAGE);
        this.RETURN_CODE = RETURN_CODE;
        this.RETURN_MESSAGE = RETURN_MESSAGE;
    }
}
