package com.xtelsolution.xmec.views.widget;

/**
 * Created by Admin on 1/24/2017.
 */

public class GlobalHolder {

    private PickerManager pickerManager;

    private static GlobalHolder ourInstance = new GlobalHolder();

    public static GlobalHolder getInstance() {
        return ourInstance;
    }

    private GlobalHolder() {
    }


    public PickerManager getPickerManager() {
        return pickerManager;
    }

    public void setPickerManager(PickerManager pickerManager) {
        this.pickerManager = pickerManager;
    }
}
