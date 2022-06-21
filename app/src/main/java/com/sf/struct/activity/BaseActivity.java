package com.sf.struct.activity;

import android.os.Bundle;

import com.sf.struct.tool.StatusBarUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();

        StatusBarUtils.setDeepStatusBar(true, this);//透明状态栏
    }

    public void hideActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
}
