package com.example.gifify_challenge.utils;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.example.gifify_challenge.ui.dialogs.DialogBase;
import com.google.android.material.snackbar.Snackbar;

public class Util {

    private FragmentManager fragmentManager;

    public static void setActionSnackBar(View view, String message, String btnTitle, int duration, View.OnClickListener action) {
        Snackbar snackbar = Snackbar.make(view, message, duration)
                .setAction(btnTitle, action);
        snackbar.show();
    }

}
