package com.movies;


import android.app.Dialog;
import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import javax.inject.Inject;

public class AppPresenter {

    Dialog progressDialog;


    @Inject
    public AppPresenter() {
    }

    public void createLoadingDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.progress);
        progressDialog = builder.create();
    }

    public void showLoading() {
        progressDialog.show();
    }

    public void stopLoading() {
        progressDialog.dismiss();
    }


}
