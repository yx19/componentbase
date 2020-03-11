package com.smartlab.componentbase.base.presenter;


import com.smartlab.componentbase.base.view.BaseView;

public interface BasePresenter<T extends BaseView> {
    /**
     * 添加view
     * */
    void attachView(T view);

    /**
     * 移除view
     * */
    void detachView();
}
