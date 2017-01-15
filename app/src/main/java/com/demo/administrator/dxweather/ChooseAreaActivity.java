package com.demo.administrator.dxweather;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.demo.administrator.view.SlidingMenu;

public class ChooseAreaActivity extends AppCompatActivity {

    private SlidingMenu slidingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_area);
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        iniView();
    }

    private void iniView() {
        slidingMenu = (SlidingMenu) findViewById(R.id.left_Menu);
    }

    public void toggleMenu(View view) {
        slidingMenu.toggle();
    }
}
