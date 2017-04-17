package cjx.com.diary.thirdtools.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

import cjx.com.diary.thirdtools.ImageInterface;

/**
 * Created by bear on 2017/4/13.
 */

public class GlideImp implements ImageInterface {


    private void display(Context context, ImageView imageView, String url) {

        Glide.with(context).load(url).dontAnimate().into(imageView);
    }

    @Override
    public void displayImage(Context context, ImageView imageView, String url) {
        display(context, imageView, url);
    }

    @Override
    public void displayImage(Context context, ImageView imageView, File file) {
        displayImage(context, imageView, file);
    }
}
