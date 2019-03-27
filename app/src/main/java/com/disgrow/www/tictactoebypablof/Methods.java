package com.disgrow.www.tictactoebypablof;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class Methods {

    /*This class contains the set of methods with
            recurrent use into the application.*/

    public static Bitmap drawableToBitmap(Drawable drawable, int widthBm, int heightBm) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(widthBm, heightBm, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(widthBm, heightBm, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);

        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static void setParamsView(View v,int w, int h){

        ViewGroup.LayoutParams params = v.getLayoutParams();
        if(w!=0)params.width = w;
        if(h!=0)params.height = h;
        v.setLayoutParams(params);

    }

    public static void setParamsView(View v,int w, int h, int left, int top, int right, int botton){

        ViewGroup.LayoutParams params = v.getLayoutParams();
        if(w!=0)params.width = w;
        if(h!=0)params.height = h;
        v.setLayoutParams(params);

        setMargenes(v,left,top,right,botton);
    }

    public static void setTextViewProperties(TextView tv, float textSize, int color, Typeface font, String text,int left, int top, int right, int botton){

        if(textSize!=0)
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        if(color!=0)
            tv.setTextColor(color);

        if(font!=null)
            tv.setTypeface(font);

        if(text!=null)
            tv.setText(text);

        setMargenes(tv,left,top,right,botton);

    }

    public static void setMargenes(View v, int izquierda, int arriba, int derecha, int abajo) {
        ViewGroup.MarginLayoutParams llhelpParams = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        llhelpParams.setMargins(izquierda, arriba, derecha, abajo);
    }

    public static int getHeightScreen(Activity activity, Resources resources) {

        int heightScreen;

        WindowManager windowManager = (WindowManager) activity.getApplication().getSystemService(Context.WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        Point outPoint = new Point();

        if (Build.VERSION.SDK_INT >= 19) {
            display.getRealSize(outPoint);
        }else {
            display.getSize(outPoint);
        }
        if (outPoint.y > outPoint.x) {
            heightScreen = outPoint.y;
        } else {
            heightScreen = outPoint.x;
        }

        int result = 0;
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }

        heightScreen = heightScreen - result;
        return heightScreen;
    }

    public static int getWidthScreen(Activity activity){

        int widthScreen;

        WindowManager windowManager = (WindowManager) activity.getApplication().getSystemService(Context.WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        Point outPoint = new Point();

        if (Build.VERSION.SDK_INT >= 19) {
            display.getRealSize(outPoint);
        }else {
            display.getSize(outPoint);
        }

        if (outPoint.y > outPoint.x) {
            widthScreen = outPoint.x;
        } else {
            widthScreen = outPoint.y;
        }

        return widthScreen;
    }

}
