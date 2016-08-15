package com.example.a13051_000.buffetmealsystem;

import android.app.ActivityManager;
import android.app.Application;

import com.example.a13051_000.buffetmealsystem.fresco.LollipopBitmapMemoryCacheParamsSupplier;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

/**
 * Created by shubin on 2016/8/15.
 */
public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        initFresco();
    }
    private void initFresco() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig
                .newBuilder(getApplicationContext())
                .setBitmapMemoryCacheParamsSupplier(new LollipopBitmapMemoryCacheParamsSupplier(activityManager))
                .build();

        Fresco.initialize(getApplicationContext(), imagePipelineConfig);
    }
}
