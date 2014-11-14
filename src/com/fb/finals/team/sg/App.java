package com.fb.finals.team.sg;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class App extends Application {
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc()
                .displayer(new FadeInBitmapDisplayer(1000))
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(this)
                .threadPoolSize(5)
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
    }

}
