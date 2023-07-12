package com.xdf.monitor.bitmap;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.os.MessageQueue;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;


public class MonitorImageView extends ImageView implements MessageQueue.IdleHandler {

    public static final int MAX_ALARM_IMAGE_SIZE = 1024;

    public MonitorImageView(Context context, AttributeSet attrs){
         super(context,attrs);
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        monitor();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        monitor();
    }

    private void monitor(){
        Looper.myQueue().removeIdleHandler(this);
        Looper.myQueue().addIdleHandler(this);
    }

    @Override
    public boolean queueIdle() {
        checkDrawable();
        return false;
    }

    private void checkDrawable(){
        Drawable drawable = getDrawable();
        if(drawable==null) return;
        int drableHeight = drawable.getIntrinsicHeight();
        int drawableWidth = drawable.getIntrinsicWidth();
        int viewHeight = getMeasuredHeight();
        int viewWidth = getMeasuredWidth();
        int imageSize = calculateImageSize(drawable);
        if(imageSize> MAX_ALARM_IMAGE_SIZE){
            Log.e("MonitorImageView","图片size大小超标了");
        }
        if(drawableWidth>100 || drableHeight>100){
            Log.e("MonitorImageView","图片尺寸超标了");
        }


    }

    private int calculateImageSize(Drawable drawable){
        if (drawable instanceof BitmapDrawable){
            return ((BitmapDrawable) drawable).getBitmap().getByteCount();
        }
        return 0;
    }
}
