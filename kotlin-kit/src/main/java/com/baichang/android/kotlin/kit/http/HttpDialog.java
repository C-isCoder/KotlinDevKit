package com.baichang.android.kotlin.kit.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import java.lang.ref.WeakReference;

/**
 * Created by iCong. Time:2016/12/5-11:36.
 */

public class HttpDialog {

    private static volatile ProgressDialog sDialog = null;
    private final static String defTips = "正在加载...";

    public static void show(Context context) {
        show(context, null);
    }

    public static void show(Context context, String tips) {
        WeakReference<Context> wc = new WeakReference<>(context);
        if (sDialog == null) {
            synchronized (HttpDialog.class) {
                if (sDialog == null) {
                    sDialog = new ProgressDialog(wc.get());
                    sDialog.setCanceledOnTouchOutside(false);
                    sDialog.setCancelable(false);
                }
            }
        }
        try {
            sDialog.setMessage(TextUtils.isEmpty(tips) ? defTips : tips);
            sDialog.show();
        } catch (Exception ignore) {
        }
    }

    public static void show(Context context, int tipsRes) {
        WeakReference<Context> wc = new WeakReference<>(context);
        if (sDialog == null) {
            synchronized (HttpDialog.class) {
                if (sDialog == null) {
                    sDialog = new ProgressDialog(wc.get());
                    sDialog.setCanceledOnTouchOutside(false);
                    sDialog.setCancelable(false);
                }
            }
        }
        try {
            sDialog.setMessage(context.getResources().getString(tipsRes));
            sDialog.show();
        } catch (Exception ignore) {
        }
    }

    public static void dismiss() {
        if (sDialog != null) {
            try {
                sDialog.dismiss();
                sDialog = null;
            } catch (Exception ignore) {
            }
        }
    }
}
