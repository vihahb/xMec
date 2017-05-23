package com.xtelsolution.xmec.xmec.views.widget;

/**
 * Created by Admin on 5/23/2017.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class CarouselLinearLayout extends LinearLayout {
    private float scale = 1f;

    public CarouselLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.invalidate();
    }

    public CarouselLinearLayout(Context context) {
        super(context);
        this.invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // The main mechanism to display scale animation, you can customize it as your needs
        int w = this.getWidth();
        int h = this.getHeight();
        canvas.scale(scale, scale, w / 2, h / 2);
    }
}