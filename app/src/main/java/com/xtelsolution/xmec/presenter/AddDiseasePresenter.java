package com.xtelsolution.xmec.presenter;

import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.CallbacListener;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.model.DiseaseModel;
import com.xtelsolution.xmec.model.MedicineModel;
import com.xtelsolution.xmec.model.REQ_Add_Disease;
import com.xtelsolution.xmec.model.REQ_Medicine;
import com.xtelsolution.xmec.model.RESP_ID;
import com.xtelsolution.xmec.views.inf.IAddIllnessView;

import java.util.List;

/**
 * Created by phimau on 3/7/2017.
 */

public class AddDiseasePresenter extends BasePresenter {
    private static final String TAG = "AddDiseasePresenter";
    private final int ADD_DISEASE = 1;
    private IAddIllnessView view;
//    private final int =1;

    public AddDiseasePresenter(IAddIllnessView view) {
        this.view = view;
    }

    //    int idMedical, String name, int idDisease, String note, final List<REQ_Medicine> medicines
    public void addDeisease(final Object... param) {
        view.showProgressDialog("Đang thêm bệnh");
        String url = Constant.SERVER_XMEC + Constant.DISEASE;
        final int idMedical = (int) param[1];
        final String name = (String) param[2];
        final int idDisease = (int) param[3];
        final String note = (String) param[4];
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
//                handlerError(view, error, ADD_DISEASE, param);
                if (error.getCode() == 2) {
//                                handlerError(view, error, DeleteFriend, friend_uid);
                    CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                        @Override
                        public void onSuccess(RESP_Login success) {
                            addDeisease(ADD_DISEASE, idMedical, name, idDisease, note, medicines);
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

    private void addMedicineReal(int uidDisease, String name, int idmedicine) {
        xLog.e(TAG, "addMedicineReal: " + Constant.LOGPHI + "idDisease  " + uidDisease + "----name " + name + "----- id_medical" + idmedicine);
        String urlMedicine = Constant.SERVER_XMEC + Constant.MEDICINE;
        xLog.e(TAG, "addMedicineReal: " + Constant.LOGPHI + "url search medicine " + urlMedicine);
        REQ_Medicine medicine = new REQ_Medicine(name, idmedicine);
        MedicineModel.getInstance().addMedicine(urlMedicine, JsonHelper.toJson(medicine), LoginManager.getCurrentSession(), new ResponseHandle<RESP_ID>(RESP_ID.class) {
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

}

