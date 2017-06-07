package com.xtelsolution.xmec.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.dd.processbutton.iml.SubmitProcessButton;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.xmec.views.widget.ProgressGenerator;

/**
 * Created by Admin on 5/31/2017.
 */

public class DialogUtil {
    public static Dialog DiaLodAddFriend(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_friend);
        final TextView editMessage = (TextView) dialog.findViewById(R.id.numberPhone);

        final ActionProcessButton btnSend = (ActionProcessButton) dialog.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSend.setProgress(50);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnSend.setProgress(100);
                        btnSend.setEnabled(true);
                    }
                }, 2000);
                btnSend.setEnabled(false);
                editMessage.setEnabled(false);
            }
        });
        return dialog;
    }


}
