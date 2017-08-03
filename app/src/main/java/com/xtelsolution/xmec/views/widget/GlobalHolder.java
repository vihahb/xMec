package com.xtelsolution.xmec.views.widget;

/**
 * Created by Admin on 1/24/2017.
 */

public class GlobalHolder {

    private static GlobalHolder ourInstance = new GlobalHolder();
    private PickerManager pickerManager;

    private GlobalHolder() {
    }

    public static GlobalHolder getInstance() {
        return ourInstance;
    }

    public PickerManager getPickerManager() {
        return pickerManager;
    }

    public void setPickerManager(PickerManager pickerManager) {
        this.pickerManager = pickerManager;
    }
}
