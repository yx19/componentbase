package com.smartlab.componentbase.base.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;

import com.smartlab.componentbase.ApplicationController;
import com.smartlab.componentbase.base.presenter.BasePresenter;
import com.smartlab.componentbase.base.view.BaseView;
import com.smartlab.componentbase.utils.StatusBarSetting;
import com.smartlab.componentbase.utils.SweetAlertDialogHelper;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ARouter.getInstance().inject(this);
        mPresenter = createPresenter();
        if (mPresenter != null)
            mPresenter.attachView(this);
        ApplicationController.getInstance().addActivity(this);
        initEventAndData();
        useNightMode(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
        dimisstipsDialog();
        ApplicationController.getInstance().removeActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dimisstipsDialog();
    }

    @Override
    public void dimisstipsDialog() {
        SweetAlertDialogHelper.getInstence().dismiss();
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void useNightMode(boolean isNight) {
        StatusBarSetting.setStatusBar(this, isNight);
    }

    @Override
    public void showtipsDialog(String str, int type) {
        SweetAlertDialogHelper.getInstence().tipsDialogWithNoBtn(str, this, type);
    }

    /**
     * 获取布局
     */
    protected abstract int getLayout();

    /**
     * 初始化事件和数据
     */
    protected abstract void initEventAndData();

    /**
     * 创建presenter
     */
    protected abstract T createPresenter();
}
