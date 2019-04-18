package com.example.awarddialdemo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @param
 * @author: 闫沛鑫
 * @date: 2019-04-17 17:21
 * @return
 */
@Data
@AllArgsConstructor
public class ResponseVO {
    private Integer statusCode;
    private String Message;
    private Object data;

    public ResponseVO(){
        super();
        setStatusCode(200);
        setMessage("ok");
    }

    public ResponseVO(Object data) {
        super();
        setStatusCode(200);
        setMessage("ok");
        setData(data);
    }
}
