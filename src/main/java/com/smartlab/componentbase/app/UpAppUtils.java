package com.smartlab.componentbase.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;
import com.vector.update_app.listener.ExceptionHandler;
import com.vector.update_app.service.DownloadService;
import com.vector.update_app.utils.AppUpdateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author：xxl
 * @Created in：2019/5/31
 */
public class UpAppUtils {
    private Activity activity;
    private String url;
    private boolean isShowDownloadProgress;
    private static final String TAG = "updata";

    public UpAppUtils(Activity activity, String updateurl) {
        this.activity = activity;
        this.url = updateurl;
    }


    /**
     * 最简方式
     */
    public void updateApp() {
        new UpdateAppManager
                .Builder()
                //当前Activity
                .setActivity(activity)
                //更新地址
                .setUpdateUrl(url)
                .handleException(new ExceptionHandler() {

                    public void onException(Exception e) {
                        e.printStackTrace();
                    }
                })
                //实现httpManager接口的对象
                .setHttpManager(new UpdateAppHttpUtil())
                .setPost(true)
                .build()
                .update();
    }

    /**
     * 自定义接口协议+自定义对话框
     */
    public void updateDiy2() {
        //不显示下载进度
        isShowDownloadProgress = true;
        diyUpdate();

    }

    private void diyUpdate() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

        Map<String, String> params = new HashMap<String, String>();

        params.put("appKey", "ab55ce55Ac4bcP408cPb8c1Aaeac179c5f6f");
        params.put("appVersion", AppUpdateUtils.getVersionName(activity.getApplicationContext()));
        Log.e("infoo12345", AppUpdateUtils.getVersionName(activity.getApplicationContext()));

        params.put("key1", "value2");
        params.put("key2", "value3");

        new UpdateAppManager
                .Builder()
                //必须设置，当前Activity
                .setActivity(activity)
                //必须设置，实现httpManager接口的对象
                .setHttpManager(new UpdateAppHttpUtil())
                //必须设置，更新地址
                .setUpdateUrl(url)

                //以下设置，都是可选
                //设置请求方式，默认get
                .setPost(true)
                //添加自定义参数，默认version=1.0.0（app的versionName）；apkKey=唯一表示（在AndroidManifest.xml配置）
                .setParams(params)
                //设置apk下砸路径，默认是在下载到sd卡下/Download/1.0.0/test.apk
                .setTargetPath(path)
                //设置appKey，默认从AndroidManifest.xml获取，如果，使用自定义参数，则此项无效
//                .setAppKey("ab55ce55Ac4bcP408cPb8c1Aaeac179c5f6f")

                .build()
                //检测是否有新版本
                .checkNewApp(new UpdateCallback() {
                    /**
                     * 解析json,自定义协议
                     *
                     * @param json 服务器返回的json
                     * @return UpdateAppBean
                     */
                    @Override
                    protected UpdateAppBean parseJson(String json) {
                        UpdateAppBean updateAppBean = new UpdateAppBean();
                        Log.e(TAG, json);
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            Log.e("info_json", jsonObject.toString());
                            if (!jsonObject.optString("new_version").equals(AppUpdateUtils.getVersionName(activity.getApplicationContext()))) {
                                updateAppBean
                                        //（必须）是否更新Yes,No
                                        .setUpdate(jsonObject.optString("update"))
                                        //（必须）新版本号，
                                        .setNewVersion(jsonObject.optString("new_version"))
                                        //（必须）下载地址
                                        .setApkFileUrl(jsonObject.optString("apk_file_url"))
                                        //（必须）更新内容
                                        .setUpdateLog(jsonObject.optString("update_log"))
                                        //大小，不设置不显示大小，可以不设置
                                        .setTargetSize(jsonObject.optString("target_size"))
                                        //是否强制更新，可以不设置
                                        .setConstraint(jsonObject.getBoolean("constraint") ? true : false)
                                        //设置md5，可以不设置
                                        .setNewMd5(jsonObject.optString("new_md5"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return updateAppBean;
                    }

                    /**
                     * 网络请求之前
                     */
                    @Override
                    public void onBefore() {
                        CProgressDialogUtils.showProgressDialog(activity);
                    }

                    /**
                     * 网路请求之后
                     */
                    @Override
                    public void onAfter() {
                        CProgressDialogUtils.cancelProgressDialog(activity);
                    }

                    /**
                     * 没有新版本
                     */
                    @Override
                    public void noNewApp(String error) {
                        Log.e(TAG, error);
                        Toast.makeText(activity, "没有新版本", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 自定义对话框
     *
     * @param updateApp
     * @param updateAppManager
     */
    private void showDiyDialog(final UpdateAppBean updateApp, final UpdateAppManager updateAppManager) {
        String targetSize = updateApp.getTargetSize();
        String updateLog = updateApp.getUpdateLog();

        String msg = "";

        if (!TextUtils.isEmpty(targetSize)) {
            msg = "新版本大小：" + targetSize + "\n\n";
        }

        if (!TextUtils.isEmpty(updateLog)) {
            msg += updateLog;
        }

        new AlertDialog.Builder(activity)
                .setTitle(String.format("是否升级到%s版本？", updateApp.getNewVersion()))
                .setMessage(msg)
                .setPositiveButton("升级", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //显示下载进度
                        if (isShowDownloadProgress) {
                            updateAppManager.download(new DownloadService.DownloadCallback() {
                                @Override
                                public void onStart() {
                                    HProgressDialogUtils.showHorizontalProgressDialog(activity, "下载进度", false);
                                }

                                /**
                                 * 进度
                                 *
                                 * @param progress  进度 0.00 -1.00 ，总大小
                                 * @param totalSize 总大小 单位B
                                 */
                                @Override
                                public void onProgress(float progress, long totalSize) {
                                    HProgressDialogUtils.setProgress(Math.round(progress * 100));
                                }

                                /**
                                 *
                                 * @param total 总大小 单位B
                                 */
                                @Override
                                public void setMax(long total) {

                                }


                                @Override
                                public boolean onFinish(File file) {
                                    HProgressDialogUtils.cancel();
                                    return true;
                                }

                                @Override
                                public void onError(String msg) {
                                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                                    HProgressDialogUtils.cancel();

                                }

                                @Override
                                public boolean onInstallAppAndAppOnForeground(File file) {
                                    return false;
                                }
                            });
                        } else {
                            //不显示下载进度
                            updateAppManager.download();
                        }


                        dialog.dismiss();
                    }
                })
                .setNegativeButton("暂不升级", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }
}
