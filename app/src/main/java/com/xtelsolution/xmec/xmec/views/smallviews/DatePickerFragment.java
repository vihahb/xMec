package com.xtelsolution.xmec.xmec.views.smallviews;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import com.xtelsolution.xmec.common.Constant;

import java.util.Calendar;

/**
 * Created by phimau on 2/17/2017.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private Calendar calendar;
    private String time;
    private long timeinMilisecond;
    EditText etTime;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        calendar = Calendar.getInstance();
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public DatePickerFragment(EditText etTime) {
        this.etTime = etTime;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Log.e("TIME", "onDateSet: "+i+"       "+i1+"        "+i2);
        calendar.set(Calendar.YEAR,i);
        calendar.set(Calendar.MONTH,i1);
        calendar.set(Calendar.DATE,i2);
        timeinMilisecond = calendar.getTimeInMillis();
        time = Constant.getDate(timeinMilisecond);
        etTime.setText(time);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getTimeinMilisecond() {
        return timeinMilisecond;
    }

    public void setTimeinMilisecond(long timeinMilisecond) {
        this.timeinMilisecond = timeinMilisecond;
    }
}
