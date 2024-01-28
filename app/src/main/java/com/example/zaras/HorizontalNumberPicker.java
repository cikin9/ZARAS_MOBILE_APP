package com.example.zaras;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.NumberPicker;

public class HorizontalNumberPicker extends NumberPicker {

    public HorizontalNumberPicker(Context context) {
        super(context);
        init();
    }

    public HorizontalNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
    }

    @Override

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Customize text color if needed
        setTextColor(Color.BLACK);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Handle touch events if needed
        return super.onTouchEvent(event);
    }


}
