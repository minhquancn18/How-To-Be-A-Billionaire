package com.example.myproject22.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class FormatImage {

    public static Bitmap ByteToBitmap(byte[] images) {
        if (images != null) {
            images = decompress(images);
            Bitmap bitmap = BitmapFactory.decodeByteArray(images, 0, images.length);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            return bitmap;
        }
        return null;
    }

    public static byte[] decompress(byte[] data) {

        try {
            Inflater inflater = new Inflater();
            inflater.setInput(data);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
            byte[] buffer = new byte[1024];
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
            byte[] output = outputStream.toByteArray();

            return output;
        } catch (Exception e) {

        }
        return null;
    }

    public static byte[] BitmapToByte(Bitmap bitmap) {
        //
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        byte[] compressed = compress(byteArray);
        return compressed;
    }
    // compress image so that it becomes lighter ?
    public static byte[] compress(byte[] data) {

        try {
            Deflater deflater = new Deflater();
            deflater.setInput(data);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

            deflater.finish();
            byte[] buffer = new byte[1024];
            while (!deflater.finished()) {
                int count = deflater.deflate(buffer); // returns the generated code... index
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
            byte[] output = outputStream.toByteArray();
            return output;

        } catch (Exception e) {

        }
        return null;
    }


}
