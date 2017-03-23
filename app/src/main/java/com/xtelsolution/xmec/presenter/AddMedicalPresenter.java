package com.xtelsolution.xmec.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.Task;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.listener.UploadFileListener;
import com.xtelsolution.xmec.model.MedicalDirectoryModel;
import com.xtelsolution.xmec.model.RESP_ID;
import com.xtelsolution.xmec.model.REQ_Medical_Detail;
import com.xtelsolution.xmec.model.Resource;
import com.xtelsolution.xmec.xmec.views.inf.IAddMedicalView;

import java.util.List;

/**
 * Created by phimau on 2/21/2017.
 */

public class AddMedicalPresenter extends BasePresenter {
    private IAddMedicalView view;

    public AddMedicalPresenter(IAddMedicalView view) {
        this.view = view;
    }

    public void addMedicalDirectorry(String name, long beginTime, long endTime, int type, String note, List<Resource> resources) {
        if (!checkConnnecttion(view))
            return;
        String url = Constant.SERVER_XMEC + Constant.MEDICAL_REPORT_BOOK;
        view.showProgressDialog(view.getActivity().getResources().getString(R.string.add_medical));
        Log.e("ADD", "addMedicalDirectorry: " + url);
        REQ_Medical_Detail REQ_medicalDetail = new REQ_Medical_Detail();
        REQ_medicalDetail.setName(name);
        REQ_medicalDetail.setBegin_time(beginTime / 1000);
        REQ_medicalDetail.setEnd_time(endTime / 1000);
        REQ_medicalDetail.setType(type);
        REQ_medicalDetail.setNote(note);
        int size = resources.size();
        String[] listRS = new String[size];
        for (int i = 0; i < size; i++) {
            listRS[i] = resources.get(i).getServer_path();
        }
        REQ_medicalDetail.setResources(listRS);
        xLog.d("STRING" + "addMedicalDirectorry: " + JsonHelper.toJson(REQ_medicalDetail));
        MedicalDirectoryModel.getinstance().addMedicalDirectory(url, JsonHelper.toJson(REQ_medicalDetail), "V5BDuS4BFpiMjgfAZBrkQpb2FUFGX8owdAxh9G77o9dE6kXfyuhPss7M5NxyNTgKwxns6SMStxlVERmOH1n05RTvbOUOC0TBWMKR", new ResponseHandle<RESP_ID>(RESP_ID.class) {
            @Override
            public void onSuccess(RESP_ID obj) {
                view.showProgressDialog(view.getActivity().getResources().getString(R.string.add_medical_success));
                view.showToast(view.getActivity().getResources().getString(R.string.add_medical_success));
                view.onAddMedicalSuccess();
                view.dismissProgressDialog();
            }

            @Override
            public void onError(Error error) {
                xLog.e("ADD" + "onError: " + error.getMessage());
                view.dismissProgressDialog();
                switch (error.getCode()) {
                    case 2:
//                        LoginModel.getInstance().getNewSession();
                        break;
                    case -1:
                        view.showToast("Lỗi hệ thống");
                }
            }
        });
    }

    public void postImage(Bitmap bitmap, boolean isBigImage, Context context) {
        if (!checkConnnecttion(view))
            return;
        new Task.ConvertImage(context, isBigImage, new UploadFileListener() {
            @Override
            public void onSuccess(String url) {
                xLog.e("onSuccess" + "onSuccess: " + url);
                view.onUploadImageSussces(url);
            }

            @Override
            public void onError(String e) {

            }
        }).execute(bitmap);

    }
}
