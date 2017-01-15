package com.demo.administrator.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.demo.administrator.dxweather.R;
import com.demo.administrator.util.LogUtil;
import com.nineoldandroids.view.ViewHelper;

public class SlidingMenu extends HorizontalScrollView {
    private LinearLayout mWaper;
    private ViewGroup mMenu;
    private ViewGroup mContent;
    private int mScreenWidth;
    private int mMenuWidth;
    private int mMenuRightPadding = 50;
    private boolean isOpen;
    private boolean once;
    private String tag = "TAG";


    /**
     * 未使用自定义属性时调用
     */
    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    /**
     * 使用自定义属性时调用
     */
    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SlidingMenu, defStyleAttr, 0);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.SlidingMenu_rightPadding:
                    mMenuRightPadding = array.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                                    , 50, context.getResources().getDisplayMetrics()));
                    break;
            }

        }
        array.recycle();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;
    }


    /**
     * 设置子View的宽和高和自身的宽和高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!once) {
            mWaper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mWaper.getChildAt(0);
            mContent = (ViewGroup) mWaper.getChildAt(1);
            mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
            mContent.getLayoutParams().width = mScreenWidth;
            mMenuWidth = mScreenWidth - mMenuRightPadding;
            once = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    /**
     * 将Menu隐藏于左侧
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(mMenuWidth, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if (scrollX >= mMenuWidth / 2) {
                    this.smoothScrollTo(mMenuWidth, 0);
                    isOpen = false;
                    LogUtil.v(tag, "onTouchEventclose");
                } else {
                    this.smoothScrollTo(0, 0);
                    isOpen = true;
                    LogUtil.v(tag, "onTouchEventopen");
                }
        }
        return super.onTouchEvent(ev);
    }


    /**
     * 打开菜单
     */
    public void openMenu() {
        if (isOpen)
            return;
        this.smoothScrollTo(0, 0);
        isOpen = true;
        LogUtil.v(tag, "openMenu");
    }


    /**
     * 关闭菜单
     */
    public void closeMenu() {
        if (!isOpen)
            return;
        this.smoothScrollTo(mMenuWidth, 0);
        isOpen = false;
        LogUtil.v(tag, "closeMenu");
    }


    /**
     * 切换菜单
     */
    public void toggle() {
        if (isOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }


    /**
     * 设置拖动时的动画
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = l * 1.0f / mMenuWidth;
        float rightScale = 0.7f + 0.3f * scale;
        float leftScale = 1.0f - scale * 0.3f;
        float leftAlpha = 0.6f + 0.4f * (1 - scale);
        ViewHelper.setTranslationX(mMenu, mMenuWidth * scale * 0.8f);
        ViewHelper.setScaleX(mMenu, leftScale);
        ViewHelper.setScaleY(mMenu, leftScale);
        ViewHelper.setAlpha(mMenu, leftAlpha);
        ViewHelper.setPivotX(mContent, 0);
        ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
        ViewHelper.setScaleX(mContent, rightScale);
        ViewHelper.setScaleY(mContent, rightScale);

        LogUtil.v(tag, "onSrollChanged");
    }
}

