package com.xtelsolution.xmec.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.LoginModel;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.Task;
import com.xtelsolution.xmec.listener.UploadFileListener;
import com.xtelsolution.xmec.model.MedicalDirectoryModel;
import com.xtelsolution.xmec.model.RESP_ID;
import com.xtelsolution.xmec.xmec.views.MyApplication;
import com.xtelsolution.xmec.xmec.views.activity.MedicalDirectoryActivity;
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
        JsonObject object = new JsonObject();
        object.addProperty(Constant.MEDICAL_NAME,name);
        object.addProperty(Constant.MEDICAL_BEGIN_TIME,beginTime);
        object.addProperty(Constant.MEDICAL_END_TIME,endTime);
        object.addProperty(Constant.MEDICAL_TYPE,type);
        object.addProperty(Constant.MEDICAL_NOTE,note);
        object.addProperty(Constant.MEDICAL_RESOURCES, JsonHelper.toJson(resources));
        MedicalDirectoryModel.getInstance().addMedicalDirectory(url, object.toString(), LoginModel.getInstance().getSession(), new ResponseHandle<RESP_ID>(RESP_ID.class) {
            @Override
            public void onSuccess(RESP_ID obj) {
                view.onAddMedicalSuccess();
            }

            @Override
            public void onError(Error error) {
                switch (error.getCode()) {
                    case 2:
                        view.showToast("Session không hợp lệ");
                        break;
                    case -1:
                        view.showToast("Lỗi hệ thống");
                }
            }
        });
    }
    public void postImage(Bitmap bitmap, Context context){
        new Task.ConvertImage(context, false, new UploadFileListener() {
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
