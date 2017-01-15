package com.demo.administrator.dxweather;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.demo.administrator.util.LogUtil;
import com.demo.administrator.view.SlidingMenu;

public class ChooseAreaActivity extends FragmentActivity {

    private SlidingMenu slidingMenu;
    private String tag = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_choose_area);
        iniView();
    }

    private void iniView() {
        slidingMenu = (SlidingMenu) findViewById(R.id.left_Menu);
    }

    public void toggleMenu(View view) {
        slidingMenu.toggle();
    }
}
