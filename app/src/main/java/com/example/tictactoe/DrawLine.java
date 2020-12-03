package com.example.tictactoe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

public class DrawLine extends View {

    private static final float LINE_WIDTH = 30.0f;
    private final Paint paint = new Paint();
    protected Context context;
    int[] startLocation, endLocation;

    private float startingX, startingY, endingX, endingY;
    private final Rect startRect, endRect;


    public DrawLine(Context context) {
        super(context);
        this.context = context;
        startRect = new Rect();
        endRect = new Rect();
        startLocation = new int[2];
        endLocation = new int[2];
        paint.setColor(Color.RED);
        paint.setStrokeWidth(LINE_WIDTH);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(90);

    }

    public void setPointsRelativeToWindow(View startView, View endView) {

        startView.getGlobalVisibleRect(startRect);
        endView.getGlobalVisibleRect(endRect);
        startingX = (startRect.left + startRect.right) / 2f;
        endingX = (endRect.left + endRect.right) / 2f;
        startingY = (startRect.top + startRect.bottom) / 2f;
        endingY = (endRect.top + endRect.bottom) / 2f;
        invalidate();
    }

    public void setPointsRelativeToParent(View startView, View endView, ViewGroup root) {

        startView.getDrawingRect(startRect);
        root.offsetDescendantRectToMyCoords(startView,startRect);
        endView.getDrawingRect(endRect);
        root.offsetDescendantRectToMyCoords(endView,endRect);
        startingX = (startRect.left + startRect.right) / 2f;
        endingX = (endRect.left + endRect.right) / 2f;
        startingY = (startRect.top + startRect.bottom) / 2f;
        endingY = (endRect.top + endRect.bottom) / 2f;
        invalidate();
    }
    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawLine(startingX, startingY, endingX, endingY, paint);

    }

}
