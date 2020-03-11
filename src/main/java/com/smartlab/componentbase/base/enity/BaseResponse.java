package com.smartlab.componentbase.base.enity;

import java.io.Serializable;

/**
 * @author：xxl
 * @Created in：2019/4/3
 */
public class BaseResponse<T> implements Serializable {

    private boolean res;
    private T data;
    private String exception;
    private String activation;
    private String userId;
    private T message;

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public BaseResponse<T> setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public boolean isRes() {
        return res;
    }

    public BaseResponse<T> setRes(boolean res) {
        this.res = res;
        return this;
    }

    public T getData() {
        return data;
    }

    public BaseResponse<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String getException() {
        return exception;
    }

    public BaseResponse<T> setException(String exception) {
        this.exception = exception;
        return this;
    }

    public String getActivation() {
        return activation;
    }

    public BaseResponse<T> setActivation(String activation) {
        this.activation = activation;
        return this;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "res=" + res +
                ", data=" + data +
                ", exception='" + exception + '\'' +
                '}';
    }
}




































































































































































































































































































































































































