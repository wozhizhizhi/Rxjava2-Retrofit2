package com.aji.retrofitlib.util;

import android.content.Context;

import com.aji.retrofitlib.CustomProgressDialog;
import com.aji.retrofitlib.R;

/**
 * Description:创建进度条工具类
 */

public class CommonDialogUtils
{
    //  加载进度的dialog
    private CustomProgressDialog mProgressDialog;

    /**
     * 显示ProgressDialog
     */
    public void showProgress(Context context, String msg)
    {
       /* if (context == null || context.isFinishing()) {
            return;
        }*/
        if (mProgressDialog == null)
        {
            mProgressDialog = new CustomProgressDialog.Builder(context)
                    .setTheme(R.style.ProgressDialogStyle)
                    .setMessage(msg)
                    .build();
        }
        if (mProgressDialog != null && !mProgressDialog.isShowing())
        {
            mProgressDialog.show();
        }
    }

    /**
     * 显示ProgressDialog
     */
    public void showProgress(Context context)
    {
        /*if (activity == null || activity.isFinishing()) {
            return;
        }*/
        if (mProgressDialog == null)
        {
            mProgressDialog = new CustomProgressDialog.Builder(context)
                    .setTheme(R.style.ProgressDialogStyle)
                    .build();
        }
        if (mProgressDialog != null && !mProgressDialog.isShowing())
        {
            mProgressDialog.show();
        }
    }

    /**
     * 取消ProgressDialog
     */
    public void dismissProgress()
    {
        if (mProgressDialog != null && mProgressDialog.isShowing())
        {
            mProgressDialog.dismiss();
        }
    }
}
