package com.example.romario.socialdirectcam;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

/**
 * Created by romario on 04/03/17.
 */
public class ImageActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_activity);

        mImageView = (ImageView) findViewById(R.id.imageView);

        String filename = getIntent().getStringExtra("image");

        if(filename != null && new File(filename).exists()) {

            try {
                ExifInterface exif = new ExifInterface(filename);

                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                Matrix matrix = new Matrix();
                matrix.postRotate(exifToDegrees(orientation));
                Bitmap myBitmap = BitmapFactory.decodeFile(filename);
                myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(), matrix, true);
                mImageView.setImageBitmap(myBitmap);

            } catch (IOException e) {
                Log.e("ImageActivity.onCreate()", e.getMessage());
            }
        }
    }

    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) { return 90; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }
        return 0;
    }

}
