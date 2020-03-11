package com.smartlab.componentbase.base.enity;

/**
 * @author：xxl
 * @Created in：2019-05-29
 */
public class UserEntity {
    /**
     * 名称
     */
    private String name;

    /**
     * 登录账号
     */
    private String userNumber;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 描述
     */
    private String description;

    /**
     * 录⼊时间
     */
    private String inputTime;

    public String getName() {
        return name;
    }

    public UserEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public UserEntity setUserNumber(String userNumber) {
        this.userNumber = userNumber;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public UserEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getInputTime() {
        return inputTime;
    }

    public UserEntity setInputTime(String inputTime) {
        this.inputTime = inputTime;
        return this;
    }
}
