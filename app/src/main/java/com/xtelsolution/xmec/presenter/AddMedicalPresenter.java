package com.xtelsolution.xmec.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.LoginModel;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.Task;
import com.xtelsolution.xmec.listener.UploadFileListener;
import com.xtelsolution.xmec.model.MedicalDirectoryModel;
import com.xtelsolution.xmec.model.RESP_ID;
import com.xtelsolution.xmec.model.RESP_MEDICAL_DETAIL;
import com.xtelsolution.xmec.xmec.views.inf.IAddMedicalView;

import java.util.List;

/**
 * Created by phimau on 2/21/2017.
 */

public class AddMedicalPresenter {
    private IAddMedicalView view;
    public AddMedicalPresenter(IAddMedicalView view){
        this.view =view;
    }
    public void addMedicalDirectorry(String name,long beginTime,long endTime,int type,String note,List<String> resources){
        String url = Constant.SERVER_XMEC+Constant.ADD_MEDICAL;
        Log.e("ADD", "addMedicalDirectorry: "+url);
        RESP_MEDICAL_DETAIL resp_medical_detail = new RESP_MEDICAL_DETAIL();
        resp_medical_detail.setName(name);
        resp_medical_detail.setBegin_time(beginTime/1000);
        resp_medical_detail.setEnd_time(endTime/1000);
        resp_medical_detail.setType(type);
        resp_medical_detail.setNote(note);
        int size =resources.size();
        String[] listRS = new String[size];
        for (int i = 0; i <size; i++) {
            listRS[i]=resources.get(i);
        }
        resp_medical_detail.setResources(listRS);
        Log.d("STRING", "addMedicalDirectorry: "+JsonHelper.toJson(resp_medical_detail));
        MedicalDirectoryModel.getInstance().addMedicalDirectory(url,JsonHelper.toJson(resp_medical_detail), LoginModel.getInstance().getSession(), new ResponseHandle<RESP_ID>(RESP_ID.class) {
            @Override
            public void onSuccess(RESP_ID obj) {
                view.onAddMedicalSuccess();
            }

            @Override
            public void onError(Error error) {
                Log.e("ADD", "onError: "+error.getMessage() );
                switch (error.getCode()) {
                    case 2:
                        Log.d("EEE", "onError: "+error.toString());
                        break;
                    case -1:
                        view.showToast("Lỗi hệ thống");
                }
            }
        });
    }
    public void postImage(Bitmap bitmap,boolean isBigImage, Context context){
        new Task.ConvertImage(context, isBigImage, new UploadFileListener() {
            @Override
            public void onSuccess(String url) {
                Log.e("onSuccess", "onSuccess: "+url);
                view.onUploadImageSussces(url);
            }
            @Override
            public void onError(String e) {

            }
        }).execute(bitmap);

    }
}
