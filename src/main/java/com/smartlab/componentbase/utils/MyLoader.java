package com.smartlab.componentbase.utils;

import android.content.Context;
import android.widget.ImageView;


import com.youth.banner.loader.ImageLoader;

/**
 * @author：xxl
 * @Created in：2019/1/22
 */
public class MyLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
//        Glide.with(context).load(path).into(imageView);
    }
}
