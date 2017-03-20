package com.xtelsolution.xmec.presenter;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.LoginModel;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.model.DiseaseModel;
import com.xtelsolution.xmec.model.MedicineModel;
import com.xtelsolution.xmec.model.REQ_Add_Disease;
import com.xtelsolution.xmec.model.REQ_Disease;
import com.xtelsolution.xmec.model.REQ_Medicine;
import com.xtelsolution.xmec.model.RESP_ID;
import com.xtelsolution.xmec.model.RESP_List_Disease;
import com.xtelsolution.xmec.model.RESP_Medicine;
import com.xtelsolution.xmec.model.entity.Disease;
import com.xtelsolution.xmec.xmec.views.inf.IAddIllnessView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phimau on 3/7/2017.
 */

public class AddDiseasePresenter {
    private IAddIllnessView view;

    public AddDiseasePresenter(IAddIllnessView view) {
        this.view = view;
    }

    public void addDeisease(int idMedical, String name, int idDisease, String note, List<REQ_Medicine> medicines) {
        view.showProgressDialog("Đang thêm bệnh");
        String url = Constant.SERVER_XMEC + Constant.DISEASE;
        REQ_Add_Disease disease = new REQ_Add_Disease(idMedical, name, idDisease, note,medicines);
        xLog.e(Constant.LOGPHI + JsonHelper.toJson(disease));
        DiseaseModel.getInstance().addDisease(url, JsonHelper.toJson(disease), Constant.LOCAL_SECCION, new ResponseHandle<RESP_ID>(RESP_ID.class) {
            @Override
            public void onSuccess(RESP_ID obj) {
                view.dismissProgressDialog();
                view.onAddDiseaseSuccess(obj.getId());
            }
            @Override
            public void onError(Error error) {
                view.dismissProgressDialog();
                Log.e("ERR", "onError: " + error.getCode());
                switch (error.getCode()) {
                    case 2:
                        view.showToast("Session không hợp lệ");
                        break;
                    case -1:
                        view.showToast(error.getMessage());
                }
            }
        });
    }

//    public void addMedicine(int idMedical, int uidDisease, int idDisease, String diseaseNmame, final String medicineName, final int idmedicine, String note) {
//        view.showProgressDialog("Đang thêm thuốc");
//        if (uidDisease == -1) {
//            String url = Constant.SERVER_XMEC + Constant.DISEASE;
//            REQ_Add_Disease disease = new REQ_Add_Disease(idMedical, diseaseNmame, idDisease, note);
//            xLog.e(Constant.LOGPHI + JsonHelper.toJson(disease));
//            DiseaseModel.getInstance().addDisease(url, JsonHelper.toJson(disease), Constant.LOCAL_SECCION, new ResponseHandle<RESP_ID>(RESP_ID.class) {
//                @Override
//                public void onSuccess(RESP_ID obj) {
//                    addMedicineReal(obj.getId(),medicineName,idmedicine);
//                }
//
//                @Override
//                public void onError(Error error) {
//                    Log.e("ERR", "onError: " + error.getCode());
//                    switch (error.getCode()) {
//                        case 2:
//                            view.showToast("Session không hợp lệ");
//                            break;
//                        case -1:
//                            view.showToast(error.getMessage());
//                    }
//                }
//            });
//        }
//    }

//    private void addMedicineReal(int uidDisease, String name, int idmedicine) {
//        xLog.e(Constant.LOGPHI + "idDisease  " + uidDisease + "----name " + name + "----- id_medical" + idmedicine);
//        String urlMedicine = Constant.SERVER_XMEC + Constant.MEDICINE;
//        xLog.e(Constant.LOGPHI + "url search medicine " + urlMedicine);
//        REQ_Medicine medicine = new REQ_Medicine(uidDisease, name, idmedicine);
//        MedicineModel.getInstance().addMedicine(urlMedicine, JsonHelper.toJson(medicine), Constant.LOCAL_SECCION, new ResponseHandle<RESP_ID>(RESP_ID.class) {
//            @Override
//            public void onSuccess(RESP_ID obj) {
//                view.onAddMedicineSuccess(obj.getId());
//                view.dismissProgressDialog();
//            }
//
//            @Override
//            public void onError(Error error) {
//                view.dismissProgressDialog();
//                Log.e("ERR", "onError: " + error.getCode());
//                switch (error.getCode()) {
//                    case 2:
//                        view.showToast("Session không hợp lệ");
//                        break;
//                    case -1:
//                        view.showToast(error.getMessage());
//                }
//            }
//        });
//    }
}

