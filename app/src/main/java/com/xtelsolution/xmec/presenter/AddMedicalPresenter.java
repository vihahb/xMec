package com.xtelsolution.xmec.presenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;

import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.CallbacListener;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.Task;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.listener.UploadFileListener;
import com.xtelsolution.xmec.model.MedicalDirectoryModel;
import com.xtelsolution.xmec.model.REQ_Medical_Detail;
import com.xtelsolution.xmec.model.RESP_ID;
import com.xtelsolution.xmec.model.RESP_Medical;
import com.xtelsolution.xmec.model.Resource;
import com.xtelsolution.xmec.views.inf.IAddMedicalView;

import java.util.List;

/**
 * Created by phimau on 2/21/2017.
 */

public class AddMedicalPresenter extends BasePresenter {
    private static final String TAG = "AddMedicalPresenter";
    private final int ADDMEDICAL = 1;
    private IAddMedicalView view;

    public AddMedicalPresenter(IAddMedicalView view) {
        this.view = view;
    }

    //    String name,long beginTime,long endTime,int type,String note,List<Resource> resources
    public void addMedicalDirectorry(final Object... param) {
        final String name = (String) param[1];
        final long beginTime = (long) param[2];
        final long endTime = (long) param[3];
        final int type = (int) param[4];
        final String note = (String) param[5];
        final List<Resource> resources = (List<Resource>) param[6];
        String url = Constant.SERVER_XMEC + Constant.MEDICAL_REPORT_BOOK;
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
        xLog.d(TAG, "addMedicalDirectorry: " + "STRING" + "addMedicalDirectorry: " + JsonHelper.toJson(REQ_medicalDetail));
        MedicalDirectoryModel.getinstance().addMedicalDirectory(url, JsonHelper.toJson(REQ_medicalDetail), LoginManager.getCurrentSession(), new ResponseHandle<RESP_ID>(RESP_ID.class) {
            @Override
            public void onSuccess(RESP_ID obj) {
                view.showProgressDialog(view.getActivity().getResources().getString(R.string.add_medical_success));
                view.showToast(view.getActivity().getResources().getString(R.string.add_medical_success));
                int id = obj.getId();
                RESP_Medical medical = new RESP_Medical(id, name, beginTime, endTime, type);
                view.onAddMedicalSuccess(medical);
            }

            @Override
            public void onError(Error error) {
//                handlerError(view, error, param);
                if (error.getCode() == 2) {
//                                handlerError(view, error, DeleteFriend, friend_uid);
                    CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                        @Override
                        public void onSuccess(RESP_Login success) {
                            addMedicalDirectorry(ADDMEDICAL, name, beginTime, endTime, type, note, resources);
                        }

                        @Override
                        public void onError(Error error) {
                            view.requireLogin();
                            view.showToast("Vui lòng đăng nhập để tiếp tục.");
                        }
                    });
                } else {
                    view.showToast("Có lỗi xảy ra.");
                }
            }
        });
    }

    public void checkAddMedical(String name, long beginTime, long endTime, int type, String note, List<Resource> resources) {
        if (!checkConnnecttion(view))
            return;
        view.showProgressDialog(view.getActivity().getResources().getString(R.string.add_medical));
        addMedicalDirectorry(ADDMEDICAL, name, beginTime, endTime, type, note, resources);
    }

    public void postImage(final Bitmap bitmap, boolean isBigImage, Activity context) {

//        view.showProgressDialog(view.getActivity().getResources().getString(R.string.upload_image));
        if (!checkConnnecttion(view))
            return;
        new Task.ConvertImage(context, isBigImage, new UploadFileListener() {
            @Override
            public void onSuccess(String url) {
                xLog.e(TAG, "postImage: onSuccess: " + "onSuccess: " + url);
                view.onUploadImageSussces(url, bitmap);
//                view.dismissProgressDialog();
            }

            @Override
            public void onError(String e) {
                view.onUploadImageError();
//                view.dismissProgressDialog();
            }
        }).execute(bitmap);

    }

}
