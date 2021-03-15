package com.tiens.chinads;

import android.content.Context;
import android.content.res.Resources;
import android.text.Html;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtil {
 
    private static Toast toast;
    private static int textView_id;
 
    public static void show(Context context, String str) {
        if (toast == null)
            toast = Toast.makeText(context.getApplicationContext(), str, Toast.LENGTH_LONG);
        else
            toast.setText(str);
        if (textView_id == 0)
            textView_id = Resources.getSystem().getIdentifier("message", "id", "android");
//        ((TextView) toast.getView().findViewById(textView_id)).setGravity(Gravity.CENTER);
        if(str.contains("/r/n"))
            ((TextView) toast.getView().findViewById(textView_id)).setText(str.replace("/r/n",System.getProperty("line.separator")));
        toast.show();
    }
 
    public static void show(Context context, int resId) {
        if (toast == null)
            toast = Toast.makeText(context.getApplicationContext(), resId, Toast.LENGTH_SHORT);
        else
            toast.setText(resId);
        if (textView_id == 0)
            textView_id = Resources.getSystem().getIdentifier("message", "id", "android");
        ((TextView) toast.getView().findViewById(textView_id)).setGravity(Gravity.CENTER);
        toast.show();
    }
 
}