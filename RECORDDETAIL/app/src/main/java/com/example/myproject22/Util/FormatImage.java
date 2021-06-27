package com.example.myproject22.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.myproject22.R;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

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
        YoYo.with(Techniques.FadeIn)
                .duration(2500)
                .playOn(view);


        Glide.with(context)
                .load(url)
                .thumbnail(
                        Glide.with(context)
                        .load(R.drawable.fish_gif)
                )
                .into(view);
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
