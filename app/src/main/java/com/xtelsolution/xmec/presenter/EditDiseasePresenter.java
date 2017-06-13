package com.xtelsolution.xmec.presenter;

import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;
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
    private IEditDiseaseView view;
    private final int EDITDISEASE = 1;
    private final int REMOVEMEDICINE = 2;
    private final int ADDMEDICINE = 3;
    private final int REMOVEDISEASE = 4;
    private final String TAG = "EditDiseasePresenter";

    public EditDiseasePresenter(IEditDiseaseView view) {
        this.view = view;
    }

    private void editDisease(final Object... param) {
        REQ_Edit_Disease disease = (REQ_Edit_Disease) param[1];
        String url = Constant.SERVER_XMEC + Constant.DISEASE;
        xLog.e("REEE",JsonHelper.toJson(disease));
        DiseaseModel.getInstance().updateDisease(url, JsonHelper.toJson(disease), LoginManager.getCurrentSession(), new ResponseHandle<RESP_Basic>(RESP_Basic.class) {
            @Override
            public void onSuccess(RESP_Basic obj) {
                view.onEditDiseaseSuccess();
            }

            @Override
            public void onError(Error error) {
                handlerError(view, error, param);
            }
        });

    }

    private void removeMedicine(final Object... param) {
        int id = (int) param[1];
        final int index = (int) param[2];
        String url = Constant.SERVER_XMEC + Constant.REMOVE_MEDICINE + id;
        MedicineModel.getInstance().removeMedicine(url, LoginManager.getCurrentSession(), new ResponseHandle<RESP_Basic>(RESP_Basic.class) {
            @Override
            public void onSuccess(RESP_Basic obj) {
                view.onRemoveMedicineSuccess(index);
            }

            @Override
            public void onError(Error error) {
                handlerError(view, error, param);
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
                view.onAddMedicineSuccess(new RESP_User_Medicine(obj.getId(),medicine.getName(),medicine.getId_medicine()));
            }

            @Override
            public void onError(Error error) {
                handlerError(view, error);
            }
        });
    }

    private void removeDisease(final Object... param) {
        int id = (int) param[1];
        String url =Constant.SERVER_XMEC +Constant.DISEASE+"/"+id;
        xLog.e(TAG, "addMedicineReal: " + Constant.LOGPHI + "url search medicine " + url);
        DiseaseModel.getInstance().removeDisease(url, LoginManager.getCurrentSession(), new ResponseHandle<RESP_Basic>(RESP_Basic.class) {
            @Override
            public void onSuccess(RESP_Basic obj) {
                view.onRemoveDiseaseSuccess();
            }

            @Override
            public void onError(Error error) {
                handlerError(view,error,param);
            }
        });
    }

    public void checkEditDisease(REQ_Edit_Disease disease) {
        if (!checkConnnecttion(view))
            return;
        editDisease(EDITDISEASE, disease);
    }

    public void checkRemoveMedicine(int idMedicine,int index) {
        if (!checkConnnecttion(view))
            return;
        removeMedicine(REMOVEMEDICINE, idMedicine,index);
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

    @Override
    public void onGetNewSessionSuccess(Object... param) {
        switch ((int)param[0]){
            case EDITDISEASE:
                editDisease(param);
                break;
            case REMOVEDISEASE:
                removeDisease(param);
                break;
            case REMOVEMEDICINE:
                removeMedicine(param);
                break;
            case ADDMEDICINE:
                addMedicine(param);
                break;
        }
    }
}
