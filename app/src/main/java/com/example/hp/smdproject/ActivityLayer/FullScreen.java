package com.example.hp.smdproject.ActivityLayer;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.hp.smdproject.R;

import gr.net.maroulis.library.EasySplashScreen;

/**
 * Created by HP on 01-Mar-17.
 */
public class FullScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        View easySplashScreenView = new EasySplashScreen(FullScreen.this)
                .withFullScreen()
                .withTargetActivity(HomeActivity.class)
                .withSplashTimeOut(2500)
                .withBackgroundResource(R.color.white)
                .withFooterText("Copyright 2017")
                .withLogo(R.drawable.manwa_black)
                .create();

        setContentView(easySplashScreenView);
    }
}
