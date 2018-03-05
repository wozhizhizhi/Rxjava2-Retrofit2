package com.aji.retrofitlib.util;

import android.content.Context;

/**
 * Utils初始化相关
 */
public class Utils
{

    private static Context context;

    private Utils()
    {
        throw new UnsupportedOperationException("your can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context)
    {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext()
    {
        if (context != null) return context;
        throw new NullPointerException("your should init first");
    }
}