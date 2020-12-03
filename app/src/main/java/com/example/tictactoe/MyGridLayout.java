package com.example.tictactoe;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridLayout;

public class MyGridLayout extends GridLayout {
    boolean interceptTouchEvents=false;
    public MyGridLayout(Context context) {
        super(context);
    }

    public MyGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return interceptTouchEvents;
    }

    public void setInterceptTouchEvents(boolean shouldInterceptTouchEvents){
        interceptTouchEvents=shouldInterceptTouchEvents;
    }
}
