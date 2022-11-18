package com.zzd.exception;

import com.zzd.result.ResponseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 全局异常处理类
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult error(Exception e){
        e.printStackTrace();
        return new ResponseResult(201,"出现异常");
    }

    // @ExceptionHandler(ArithmeticException.class)
    // @ResponseBody
    // public ResponseResult error(ArithmeticException e){
    //     e.printStackTrace();
    //     return new ResponseResult(201,"全局异常");
    // }
}