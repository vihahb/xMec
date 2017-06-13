package com.xtelsolution.xmec.views.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by Admin on 2/7/2017.
 */

public class KeyboardDetectorRelativeLayout extends RelativeLayout {

    public interface IKeyboardChanged {
        void onKeyboardShown();

        void onKeyboardHidden();
    }

    private ArrayList<IKeyboardChanged> keyboardListener = new ArrayList<IKeyboardChanged>();

    public KeyboardDetectorRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public KeyboardDetectorRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyboardDetectorRelativeLayout(Context context) {
        super(context);
    }

    public void addKeyboardStateChangedListener(IKeyboardChanged listener) {
        keyboardListener.add(listener);
    }

    public void removeKeyboardStateChangedListener(IKeyboardChanged listener) {
        keyboardListener.remove(listener);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int proposedheight = MeasureSpec.getSize(heightMeasureSpec);
        final int actualHeight = getHeight();

        Log.i("onMeasure", proposedheight + " " + actualHeight);
        if (actualHeight > proposedheight) {
            notifyKeyboardShown();
        } else if (actualHeight < proposedheight) {
            notifyKeyboardHidden();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void notifyKeyboardHidden() {
        for (IKeyboardChanged listener : keyboardListener) {
            listener.onKeyboardHidden();
        }
    }

    private void notifyKeyboardShown() {
        for (IKeyboardChanged listener : keyboardListener) {
            listener.onKeyboardShown();
        }
    }

}