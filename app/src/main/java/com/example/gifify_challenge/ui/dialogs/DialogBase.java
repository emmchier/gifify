package com.example.gifify_challenge.ui.dialogs;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.gifify_challenge.R;
import com.example.gifify_challenge.core.entities.GifEntity;
import com.example.gifify_challenge.databinding.DialogBaseBinding;

public class DialogBase extends DialogFragment {

    private String dialogDescription;
    private String mainActionBtnTitle;
    private String btnGoToFavourites;
    private View.OnClickListener mainActionListener;
    private View.OnClickListener directionListener;

    private DialogBaseBinding binding;

    private GifEntity gifEntity;

    public DialogBase(GifEntity gifEntity, String dialogDescription, String mainActionBtnTitle, String btnGoToFavourites,
                      View.OnClickListener mainActionListener, View.OnClickListener directionListener) {
        this.dialogDescription = dialogDescription;
        this.mainActionBtnTitle = mainActionBtnTitle;
        this.btnGoToFavourites = btnGoToFavourites;
        this.mainActionListener = mainActionListener;
        this.directionListener = directionListener;
        this.gifEntity = gifEntity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = DialogBaseBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(binding.getRoot());
        setDialogView();

        return builder.create();
    }

    private void setDialogView() {
        Glide.with(binding.getRoot())
                .load(gifEntity.getImages().getDownsized().getUrl())
                .placeholder(R.drawable.ic_baseline_videocam_24)
                .error(R.drawable.ic_baseline_videocam_24)
                .into(binding.imageViewGifDialog);
        if (dialogDescription.equals("")) {
            binding.textViewDialogDescription.setVisibility(View.GONE);
        } else {
            binding.textViewDialogDescription.setVisibility(View.VISIBLE);
            binding.textViewDialogDescription.setText(dialogDescription);
        }
        setActionBtn(mainActionBtnTitle, binding.textViewBtnActionMain, mainActionListener);
        setActionBtn(btnGoToFavourites, binding.textViewBtnSeeMore, directionListener);
        binding.textViewBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void setActionBtn(String btnText, TextView btn, View.OnClickListener listener) {
        if (!btnText.equals("")) {
            btn.setVisibility(View.VISIBLE);
            btn.setText(btnText);
            if (listener == null) {
                btn.setOnClickListener(null);
            } else {
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onClick(view);
                        dismiss();
                    }
                });
            }
        } else {
            btn.setVisibility(View.GONE);
        }
    }
}
