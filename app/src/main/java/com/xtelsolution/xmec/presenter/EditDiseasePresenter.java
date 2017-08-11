package com.xtelsolution.xmec.presenter;

import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.CallbacListener;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.model.DiseaseModel;
import com.xtelsolution.xmec.model.MedicineModel;
import com.xtelsolution.xmec.model.REQ_Add_Medicine;
import com.xtelsolution.xmec.model.REQ_Edit_Disease;
import com.xtelsolution.xmec.model.RESP_ID;
import com.xtelsolution.xmec.model.RESP_User_Medicine;
import com.xtelsolution.xmec.views.inf.IEditDiseaseView;

/**
 * Created by phimau on 4/4/2017.
 */

public class EditDiseasePresenter extends BasePresenter {
    private final int EDITDISEASE = 1;
    private final int REMOVEMEDICINE = 2;
    private final int ADDMEDICINE = 3;
    private final int REMOVEDISEASE = 4;
    private final String TAG = "EditDiseasePresenter";
    private IEditDiseaseView view;

    public EditDiseasePresenter(IEditDiseaseView view) {
        this.view = view;
    }

    private void editDisease(final Object... param) {
        final REQ_Edit_Disease disease = (REQ_Edit_Disease) param[1];
        String url = Constant.SERVER_XMEC + Constant.DISEASE;
        xLog.e("REEE", JsonHelper.toJson(disease));
        DiseaseModel.getInstance().updateDisease(url, JsonHelper.toJson(disease), LoginManager.getCurrentSession(), new ResponseHandle<RESP_Basic>(RESP_Basic.class) {
            @Override
            public void onSuccess(RESP_Basic obj) {
                view.onEditDiseaseSuccess();
            }

            @Override
            public void onError(Error error) {
                if (error.getCode() == 2) {
                    CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                        @Override
                        public void onSuccess(RESP_Login success) {
                            editDisease(EDITDISEASE, disease);
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

    private void removeMedicine(final Object... param) {
        final int id = (int) param[1];
        final int index = (int) param[2];
        String url = Constant.SERVER_XMEC + Constant.REMOVE_MEDICINE + id;
        MedicineModel.getInstance().removeMedicine(url, LoginManager.getCurrentSession(), new ResponseHandle<RESP_Basic>(RESP_Basic.class) {
            @Override
            public void onSuccess(RESP_Basic obj) {
                view.onRemoveMedicineSuccess(index);
            }

            @Override
            public void onError(Error error) {
                if (error.getCode() == 2) {
                    CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                        @Override
                        public void onSuccess(RESP_Login success) {
                            removeMedicine(REMOVEMEDICINE, id, index);
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

    private void addMedicine(Object... param) {
        final REQ_Add_Medicine medicine = (REQ_Add_Medicine) param[1];
        String urlMedicine = Constant.SERVER_XMEC + Constant.MEDICINE;
        xLog.e(TAG, "addMedicineReal: " + Constant.LOGPHI + "url search medicine " + urlMedicine);
        MedicineModel.getInstance().addMedicine(urlMedicine, JsonHelper.toJson(medicine), LoginManager.getCurrentSession(), new ResponseHandle<RESP_ID>(RESP_ID.class) {
            @Override
            public void onSuccess(RESP_ID obj) {
                view.onAddMedicineSuccess(new RESP_User_Medicine(obj.getId(), medicine.getName(), medicine.getId_medicine()));
            }

            @Override
            public void onError(Error error) {
                if (error.getCode() == 2) {
                    CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                        @Override
                        public void onSuccess(RESP_Login success) {
                            addMedicine(ADDMEDICINE, medicine);
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

    private void removeDisease(final Object... param) {
        final int id = (int) param[1];
        String url = Constant.SERVER_XMEC + Constant.DISEASE + "/" + id;
        xLog.e(TAG, "addMedicineReal: " + Constant.LOGPHI + "url search medicine " + url);
        DiseaseModel.getInstance().removeDisease(url, LoginManager.getCurrentSession(), new ResponseHandle<RESP_Basic>(RESP_Basic.class) {
            @Override
            public void onSuccess(RESP_Basic obj) {
                view.onRemoveDiseaseSuccess();
            }

            @Override
            public void onError(Error error) {
                if (error.getCode() == 2) {
                    CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                        @Override
                        public void onSuccess(RESP_Login success) {
                            removeDisease(REMOVEDISEASE, id);
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

    public void checkEditDisease(REQ_Edit_Disease disease) {
        if (!checkConnnecttion(view))
            return;
        editDisease(EDITDISEASE, disease);
    }

    public void checkRemoveMedicine(int idMedicine, int index) {
        if (!checkConnnecttion(view))
            return;
        removeMedicine(REMOVEMEDICINE, idMedicine, index);
    }

    public void checkAddMedicine(REQ_Add_Medicine medicine) {
        if (!checkConnnecttion(view))
            return;
        addMedicine(ADDMEDICINE, medicine);
    }

    public void checkRemoveDisease(int idDisease) {
        if (!checkConnnecttion(view))
            return;
        removeDisease(REMOVEDISEASE, idDisease);
    }
}
