package com.changgou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回结果实体
 */

@Data
public class Result<T> {

    private boolean flag; //是否成功
    private Integer code; //返回码
    private String message; //返回消息

    private  T data; //返回数据

    public Result(boolean flag, Integer code, String message, T data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result() {
        this.flag=true;
        this.message="执行成功";
    }
}

