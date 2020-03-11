package com.smartlab.componentbase.base.enity;

import java.io.Serializable;

/**
 * @author：xxl
 * @Created in：2019/4/3
 */
public class BaseEnity<T> implements Serializable {
    private String cmd;
    private String type;
    private BaseResponse<T> response;

    public String getCmd() {
        return cmd;
    }

    public BaseEnity<T> setCmd(String cmd) {
        this.cmd = cmd;
        return this;
    }

    public String getType() {
        return type;
    }

    public BaseEnity<T> setType(String type) {
        this.type = type;
        return this;
    }

    public BaseResponse<T> getResponse() {
        return response;
    }

    public BaseEnity<T> setResponse(BaseResponse<T> response) {
        this.response = response;
        return this;
    }

    @Override
    public String toString() {
        return "BaseEnity{" +
                "cmd='" + cmd + '\'' +
                ", type='" + type + '\'' +
                ", response=" + response +
                '}';
    }
}
