package com.xtelsolution.xmec.presenter;

import android.util.Log;

import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.LoginModel;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.model.DiseaseModel;
import com.xtelsolution.xmec.model.MedicalDirectoryModel;
import com.xtelsolution.xmec.model.RESP_Disease;
import com.xtelsolution.xmec.model.RESP_List_Disease;
import com.xtelsolution.xmec.model.REQ_Medical_Detail;
import com.xtelsolution.xmec.model.RESP_List_Disease_With_Link;
import com.xtelsolution.xmec.model.RESP_Medical_Detail;
import com.xtelsolution.xmec.model.Resource;
import com.xtelsolution.xmec.xmec.views.inf.IMedicalDetailView;

import java.util.List;

/**
 * Created by phimau on 3/6/2017.
 */

public class MedicalDetailPresenter extends BasePresenter {
    private IMedicalDetailView view;

    public MedicalDetailPresenter(IMedicalDetailView view) {
        this.view = view;
    }

    public void getDetailMedical(final int id){
        if (!checkConnnecttion(view))
            return;
        view.showProgressDialog("Đang tải");
        Log.e("sdasdadasd", "getDetailMedical: " +id);
        String url= Constant.SERVER_XMEC+Constant.MEDICAL_REPORT_BOOK+"/"+id;
        MedicalDirectoryModel.getinstance().getMedicalDirectoryDetail(url, "V5BDuS4BFpiMjgfAZBrkQpb2FUFGX8owdAxh9G77o9dE6kXfyuhPss7M5NxyNTgKwxns6SMStxlVERmOH1n05RTvbOUOC0TBWMKR", new ResponseHandle<RESP_Medical_Detail>(RESP_Medical_Detail.class) {
            @Override
            public void onSuccess(RESP_Medical_Detail obj) {
                view.onLoadMedicalFinish(obj);
                getListIllness(id);
            }

            @Override
            public void onError(Error error) {
                view.dismissProgressDialog();
                xLog.e(error.getMessage());
                switch (error.getCode()) {
                    case 2:
                        view.showToast("Session không hợp lệ");
                        break;
                    case -1:
                        view.showToast("Lỗi hệ thống ");
                }
            }
        });
    }
    public void getListIllness(int id){
        if (!checkConnnecttion(view))
            return;
        String url =Constant.SERVER_XMEC+Constant.ILLNESS+"/"+id;
        xLog.e(Constant.LOGPHI+ "url"+url);
        DiseaseModel.getInstance().getListIllness(url, Constant.LOCAL_SECCION, new ResponseHandle<RESP_List_Disease_With_Link>(RESP_List_Disease_With_Link.class) {
            @Override
            public void onSuccess(RESP_List_Disease_With_Link obj) {
                List<RESP_Disease> resp_diseases = obj.getData();
                view.onLoadListIllnessFinish(resp_diseases);
                view.dismissProgressDialog();
            }

            @Override
            public void onError(Error error) {
                view.dismissProgressDialog();
                xLog.e(Constant.LOGPHI+error.toString());
                switch (error.getCode()) {
                    case 2:
                        view.showToast("Session không hợp lệ");
                        break;
                    case -1:
                        view.showToast("Lỗi hệ thống ");
                }
            }
        });
    }
    public void updateMedicalDirectory(int id,String name,long beginTime,long endTime,int type,String note,List<Resource> resources){
        if (!checkConnnecttion(view))
            return;
        view.showProgressDialog("Đang cập nhật");
        String url = Constant.SERVER_XMEC+Constant.MEDICAL_REPORT_BOOK;
        Log.e("ADD", "addMedicalDirectorry: "+url);
        REQ_Medical_Detail REQ_medicalDetail = new REQ_Medical_Detail();
        REQ_medicalDetail.setId(id);
        REQ_medicalDetail.setName(name);
        REQ_medicalDetail.setBegin_time(beginTime/1000);
        REQ_medicalDetail.setEnd_time(endTime/1000);
        REQ_medicalDetail.setType(type);
        REQ_medicalDetail.setNote(note);
        int size =resources.size();
        String[] listRS = new String[size];
        for (int i = 0; i <size; i++) {
            listRS[i]=resources.get(i).getServer_path();
        }
        REQ_medicalDetail.setResources(listRS);
        xLog.d("STRING" + "addMedicalDirectorry: "+ JsonHelper.toJson(REQ_medicalDetail));
        MedicalDirectoryModel.getinstance().updateMedicalDirectory(url,JsonHelper.toJson(REQ_medicalDetail), "V5BDuS4BFpiMjgfAZBrkQpb2FUFGX8owdAxh9G77o9dE6kXfyuhPss7M5NxyNTgKwxns6SMStxlVERmOH1n05RTvbOUOC0TBWMKR", new ResponseHandle<RESP_Basic>(RESP_Basic.class) {
            @Override
            public void onSuccess(RESP_Basic obj) {
                view.onUpdateMedicalFinish();
                view.dismissProgressDialog();
            }
            @Override
            public void onError(Error error) {
                xLog.e("ADD"+ "onError: "+error.getMessage());
                view.dismissProgressDialog();
                switch (error.getCode()) {
                    case 2:
                        view.showToast("Phiên làm việc hết hạn");
                        break;
                    case -1:
                        view.showToast("Lỗi hệ thống");
                }
            }
        });
    }
    public void removeMedical(int id){
        if (!checkConnnecttion(view))
            return;
        view.showProgressDialog("Đang Xóa");
        String url = Constant.SERVER_XMEC+Constant.MEDICAL_REPORT_BOOK+"/"+id;
        MedicalDirectoryModel.getinstance().deleteMedicalDirectory(url, Constant.LOCAL_SECCION, new ResponseHandle<RESP_Basic>(RESP_Basic.class) {
            @Override
            public void onSuccess(RESP_Basic obj) {
                view.onRemoveMedicalSuccess();
                view.dismissProgressDialog();
            }

            @Override
            public void onError(Error error) {
                view.dismissProgressDialog();
                switch (error.getCode()){
                    case -1:
                        view.showToast("Lỗi hệ thống");
                        break;
                    case  2:
                        view.showToast("Phiên làm việc không hợp lệ");
                }
            }
        });

    }

}
