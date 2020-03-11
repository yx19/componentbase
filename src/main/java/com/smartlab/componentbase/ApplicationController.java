package com.smartlab.componentbase;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hss01248.dialog.ActivityStackManager;
import com.hss01248.dialog.StyledDialog;

import java.util.HashSet;
import java.util.Set;

public class ApplicationController extends MultiDexApplication {
    private static ApplicationController applicationController;
    private Set<Activity> activitySet;

    public synchronized static ApplicationController getInstance(){
        if (applicationController == null){
            applicationController = new ApplicationController();
        }
        return applicationController;
    }

    public Set<Activity> getAllActivitySet(){
        return activitySet;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initRouter(this);
        initDialogUtils();
//        initCloudChannel(this);

    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);

    }

    /**
     * 添加Activity
     * */
    public void addActivity(Activity act){
        if (activitySet == null){
            activitySet = new HashSet<>();
        }
        activitySet.add(act);
    }

//    /**
//     * 初始化云推送通道
//     * @param applicationContext
//     */
//    private void initCloudChannel(Context applicationContext) {
//        PushServiceFactory.init(applicationContext);
//
//    }

    /**
     * 初始化路由
     * */
    private void initRouter(ApplicationController myApplication) {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(myApplication);
    }

    /**
     * 初始化DialogUtils
     * */
    private void initDialogUtils(){
        StyledDialog.init(this);
        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                ActivityStackManager.getInstance().addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                ActivityStackManager.getInstance().removeActivity(activity);
            }
        });
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }

    /**
     * 移除Activity
     * */
    public void removeActivity(Activity act){
        if (activitySet != null){
            activitySet.remove(act);
        }
    }

    /**
     * 退出APP,结束所有Activity
     * */
    public void exitApp(){
        if (activitySet != null){
            for (Activity activity:activitySet){
                activity.finish();
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
