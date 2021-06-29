package com.example.myproject22.Util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.myproject22.R;

import java.io.ByteArrayOutputStream;

public class FormatImage {

    public static Bitmap ByteToBitmap(byte[] images) {
        if (images != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(images, 0, images.length);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            return bitmap;
        }
        return null;
    }

    public static byte[] BitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    // compress image so that it becomes lighter ?
    public static void LoadImageIntoView(ImageView view, Context context, String url) {

        if(context == null){
            return;
        }
        YoYo.with(Techniques.FadeIn)
                .duration(2000)
                .playOn(view);

        if(!((Activity)context).isFinishing()) {
            Glide.with(context)
                    .load(url)
                    .thumbnail(
                            Glide.with(context)
                                    .load(R.drawable.fish_gif)
                    )
                    .into(view);
        }
    }

    // compress image so that it becomes lighter ?
    public static void LoadImageIntoView(ImageView view, Context context, int id) {

        if(context == null){
            return;
        }
        YoYo.with(Techniques.FadeIn)
                .duration(2000)
                .playOn(view);

        if(!((Activity)context).isFinishing()) {
            Glide.with(context)
                    .load(id)
                    .thumbnail(
                            Glide.with(context)
                                    .load(R.drawable.fish_gif)
                    )
                    .into(view);
        }
    }

    public static void StopLoadImage(Context context){
        Glide.with(context).pauseRequests();
    }

    public static String convertByteToString(byte[] bytes) {
        if (bytes == null) {
            String s = "NULL";
            return s;
        } else {
            String s = Base64.encodeToString(bytes, Base64.DEFAULT);
            return s;
        }
    }

}
