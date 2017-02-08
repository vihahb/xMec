package com.xtelsolution.xmec.entity;

/**
 * Created by phimau on 1/22/2017.
 */

public class IIlness {
    private boolean isButton;

    public IIlness(boolean isButton) {
        this.isButton = isButton;
    }

    public boolean isButton() {
        return isButton;
    }

    public void setButton(boolean button) {
        isButton = button;
    }
}
