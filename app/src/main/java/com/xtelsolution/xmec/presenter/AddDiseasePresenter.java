package com.xtelsolution.xmec.presenter;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.google.gson.JsonObject;
import com.xtel.nipservicesdk.LoginManager;
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

public class AddDiseasePresenter extends BasePresenter {
    private static final String TAG = "AddDiseasePresenter";
    private IAddIllnessView view;
    private final int ADD_DISEASE = 1;
//    private final int =1;

    public AddDiseasePresenter(IAddIllnessView view) {
        this.view = view;
    }

    //    int idMedical, String name, int idDisease, String note, final List<REQ_Medicine> medicines
    public void addDeisease(final Object... param) {
        view.showProgressDialog("Đang thêm bệnh");
        String url = Constant.SERVER_XMEC + Constant.DISEASE;
        int idMedical = (int) param[1];
        String name = (String) param[2];
        int idDisease = (int) param[3];
        String note = (String) param[4];
        final List<REQ_Medicine> medicines = (List<REQ_Medicine>) param[5];
        REQ_Add_Disease disease = new REQ_Add_Disease(idMedical, name, idDisease, note, medicines);
        xLog.e(TAG, "addDeisease: " + Constant.LOGPHI + JsonHelper.toJson(disease));
        DiseaseModel.getInstance().addDisease(url, JsonHelper.toJson(disease), LoginManager.getCurrentSession(), new ResponseHandle<RESP_ID>(RESP_ID.class) {
            @Override
            public void onSuccess(final RESP_ID obj) {

//                if (medicines.size() > 0) {
//                    for (final REQ_Medicine medicine : medicines) {
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (medicine.equals(medicines.get(medicines.size() - 1)))
//                                    addMedicineReal(obj.getId(), medicine.getName(), medicine.getId_medicine(), true);
//                                else
//                                    addMedicineReal(obj.getId(), medicine.getName(), medicine.getId_medicine(), false);
//                            }
//                        }, 50);
//                    }
//                } else {
                view.onAddDiseaseSuccess(obj.getId());
//                }
            }

            @Override
            public void onError(Error error) {
                handlerError(view, error, ADD_DISEASE, param);
            }
        });
    }

    public void checkAddDisease(int idMedical, String name, int idDisease, String note, List<REQ_Medicine> medicines) {
        if (!checkConnnecttion(view)) {
            return;
        }
        addDeisease(ADD_DISEASE, idMedical, name, idDisease, note, medicines);
    }

    public void addDisease(int idMedical, String name, int idDisease, String note, List<REQ_Medicine> medicines) {
        if (!checkConnnecttion(view))
            return;
        view.showProgressDialog("Đang Thêm bệnh");
        String url = Constant.SERVER_XMEC + Constant.DISEASE;
        REQ_Add_Disease disease = new REQ_Add_Disease(idMedical, name, idDisease, note, medicines);
        xLog.e(TAG, "addDisease: " + Constant.LOGPHI + JsonHelper.toJson(disease));
        DiseaseModel.getInstance().addDisease(url, JsonHelper.toJson(disease), LoginManager.getCurrentSession(), new ResponseHandle<RESP_ID>(RESP_ID.class) {
            @Override
            public void onSuccess(RESP_ID obj) {
                view.onAddMedicineSuccess(obj.getId());
            }

            @Override
            public void onError(Error error) {
//                handlerError(view, error);
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

    private void addMedicineReal(int uidDisease, String name, int idmedicine, final boolean islast) {
        xLog.e(TAG, "addMedicineReal: " + Constant.LOGPHI + "idDisease  " + uidDisease + "----name " + name + "----- id_medical" + idmedicine);
        String urlMedicine = Constant.SERVER_XMEC + Constant.MEDICINE;
        xLog.e(TAG, "addMedicineReal: " + Constant.LOGPHI + "url search medicine " + urlMedicine);
        REQ_Medicine medicine = new REQ_Medicine(name, idmedicine);
        MedicineModel.getInstance().addMedicine(urlMedicine, JsonHelper.toJson(medicine), LoginManager.getCurrentSession(), new ResponseHandle<RESP_ID>(RESP_ID.class) {
            @Override
            public void onSuccess(RESP_ID obj) {
                if (islast) {
                    view.dismissProgressDialog();
                    view.onAddMedicineSuccess(obj.getId());
                }
            }

            @Override
            public void onError(Error error) {
//                handlerError(view, error);
            }
        });
    }


    @Override
    public void onGetNewSessionSuccess(Object... param) {
        switch ((int) param[0]) {
            case ADD_DISEASE:
                addDeisease(param);
        }
    }
}

