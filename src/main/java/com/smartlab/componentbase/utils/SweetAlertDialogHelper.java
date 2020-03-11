package com.smartlab.componentbase.utils;

import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * @author：xxl
 * @Created in：2019/4/2
 */
public class SweetAlertDialogHelper {
    private SweetAlertDialog sweetAlertDialog;
    private static SweetAlertDialogHelper sweetAlertDialogHelper;

    private SweetAlertDialogHelper() {
    }

    public static SweetAlertDialogHelper getInstence() {
        if (sweetAlertDialogHelper == null) {
            sweetAlertDialogHelper = new SweetAlertDialogHelper();
        }
        return sweetAlertDialogHelper;
    }

    /**
     * 取消dialog
     */
    public void dismiss() {
        if (sweetAlertDialog != null)
            sweetAlertDialog.dismiss();
        sweetAlertDialog = null;
    }

    /**
     * 不带按钮带文字的提示框
     */
    public void tipsDialogWithNoBtn(String str, Context context, int Type) {
        if (sweetAlertDialog == null){
            sweetAlertDialog = new SweetAlertDialog(context, Type).setContentText(str);
        } else {
            sweetAlertDialog.changeAlertType(Type);
            sweetAlertDialog.setContentText(str);
        }
        if (Type == SweetAlertDialog.ERROR_TYPE || Type == SweetAlertDialog.WARNING_TYPE || Type == SweetAlertDialog.SUCCESS_TYPE){
            sweetAlertDialog.setConfirmButton("确定", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    dismiss();
                }
            });
        }
        sweetAlertDialog.show();
    }
}
