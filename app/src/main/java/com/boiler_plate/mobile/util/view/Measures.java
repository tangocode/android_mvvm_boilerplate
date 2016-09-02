package com.boiler_plate.mobile.util.view;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by dcoellar on 2/3/16.
 */
public class Measures {

    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public static float getScreenWidth(WindowManager windowManager){
        Display display = windowManager.getDefaultDisplay();
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB_MR2) {
            return display.getWidth();  // deprecated
        } else {
            Point size1 = new Point();
            display.getSize(size1);
            return size1.x;
        }
    }
}
