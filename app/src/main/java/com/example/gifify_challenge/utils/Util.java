package com.example.gifify_challenge.utils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.gifify_challenge.R;
import com.example.gifify_challenge.core.entities.GifEntity;
import com.example.gifify_challenge.ui.dialogs.DialogBase;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import static java.security.AccessController.getContext;

/*
 * Util class for shared methods
 */
public class Util {

    // set SnackBars
    public static void setActionSnackBar(View view, String message, String btnTitle, int duration, View.OnClickListener action) {
        Snackbar snackbar = Snackbar.make(view, message, duration)
                .setAction(btnTitle, action);
        snackbar.show();
    }

    // hide keyboard
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    // share method
    public static void shareGif(GifEntity gif, Context context, FragmentManager fm) {
        DialogBase dialogBase = new DialogBase(
                gif,
                "",
                context.getString(R.string.share_title),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Glide.with(context)
                                .asBitmap()
                                .load(gif.getImages().getDownsized().getUrl())
                                .into(new CustomTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                        Uri uri = getImageUri(resource, context);
                                        Intent shareIntent = new Intent();
                                        shareIntent.setAction(Intent.ACTION_SEND);
                                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                                        shareIntent.setType("image/gif");
                                        context.startActivity(Intent.createChooser(shareIntent, "Share this GIF"));
                                    }
                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {
                                    }
                                });
                    }
                }
        );
        dialogBase.show(fm, "Share with friends");
    }

    // transform to bitmap
    private static Uri getImageUri(Bitmap inImage, Context context) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),
                inImage, UUID.randomUUID().toString() + ".png", "drawing");
        return Uri.parse(path);
    }

    // margins for recyclers
    public static int dpToPx(Context c, int dp) {
        Resources r = c.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
