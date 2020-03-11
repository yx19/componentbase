package com.smartlab.componentbase.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


import com.smartlab.componentbase.base.presenter.BasePresenter;
import com.smartlab.componentbase.base.view.BaseView;
import com.smartlab.componentbase.utils.StatusBarSetting;
import com.smartlab.componentbase.utils.SweetAlertDialogHelper;

/**
 * @author：xxl
 * @Created in：2019-05-28
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView {
    protected T mPresenter;
    private View mFragment;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null)
            mPresenter.attachView(this);
        initEventAndData();

    }

    /**
     * 获取当前fragment所依赖的Activity
     *
     * @return
     */
    public FragmentActivity getFragmentActivity() {
        return this.getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragment = inflater.inflate(getLayout(), container, false);
        return mFragment;
    }

    public View getFragmentContext() {
        return mFragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
        dimisstipsDialog();
    }

    @Override
    public void showtipsDialog(String str, int type) {
        SweetAlertDialogHelper.getInstence().tipsDialogWithNoBtn(str, getFragmentContext().getContext(), type);
    }

    @Override
    public void dimisstipsDialog() {
        SweetAlertDialogHelper.getInstence().dismiss();
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(getFragmentContext().getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void useNightMode(boolean isNight) {
        StatusBarSetting.setStatusBar(getFragmentContext().getContext(), isNight);
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
