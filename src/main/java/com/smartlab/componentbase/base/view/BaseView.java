package com.smartlab.componentbase.base.view;

public interface BaseView {
    /**
     * 提示消息
     * */
    void showMsg(String msg);

    /**
     * 使用黑暗模式
     * */
    void useNightMode(boolean isNight);

    /**
     * 加载提示框
     * */
    void showtipsDialog(String str, int type);

    /**
     * 关闭提示框
     * */
    void dimisstipsDialog();

}
