package com.cube.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "返回类")
public class Result<T>{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="code")
    private String code;
    @ApiModelProperty(value="描述")
    private String message;
    @ApiModelProperty(value="数据")
    private T data;

    public Result() {
        this.code="200";
        this.message="success";
    }
    public Result(T data) {
        this.code="200";
        this.message="success";
        this.data=data;

    }public Result(String message) {
        this.code="200";
        this.message=message;
    }
    public Result(String code , String message , T data) {
        this.code=code;
        this.message=message;
        this.data=data;
    }
    public Result(String code , String message ) {
        this.code=code;
        this.message=message;

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}