package com.xtelsolution.xmec.presenter.Fragment;

import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_None;
import com.xtelsolution.xmec.callbacks.ICommand;
import com.xtelsolution.xmec.common.NetWorkInfo;
import com.xtelsolution.xmec.model.MedicalDirectoryModel;
import com.xtelsolution.xmec.model.RESP_DrugList;
import com.xtelsolution.xmec.model.entity.DrugAddEntity;
import com.xtelsolution.xmec.model.entity.DrugEntity;
import com.xtelsolution.xmec.model.entity.GeneralEntity;
import com.xtelsolution.xmec.model.entity.SpecialEntity;
import com.xtelsolution.xmec.views.inf.IDrugDetailsFragment;

import java.util.ArrayList;

/**
 * Created by vivhp on 8/2/2017
 */

public class DrugDetailsFragmentPresenter {

    private IDrugDetailsFragment view;

    private ICommand iCmd = new ICommand() {
        @Override
        public void excute(Object... params) {
            switch ((int) params[0]) {
                case 1:
                    int id_medical_report = (int) params[1];
                    MedicalDirectoryModel.getinstance().getListDrug(id_medical_report, LoginManager.getCurrentSession(), new ResponseHandle<RESP_DrugList>(RESP_DrugList.class) {
                        @Override
                        public void onSuccess(RESP_DrugList obj) {
                            ArrayList<DrugEntity> arrayList = new ArrayList<>();
                            if (obj.getSpecial() != null && obj.getSpecial().getData().size() > 0) {
                                SpecialEntity entity = obj.getSpecial();
                                arrayList.addAll(entity.getData());
                            }

                            if (obj.getGeneral() != null && obj.getGeneral().getData().size() > 0) {
                                GeneralEntity generalEntity = obj.getGeneral();
                                arrayList.addAll(generalEntity.getData());
                            }

                            view.getDrugListSuccess(arrayList);
                        }

                        @Override
                        public void onError(Error error) {
                            if (error != null && error.getCode() == 2) {
//                                view.showToast(JsonParse.getCodeMessage(error.getCode(), "Có lỗi!"));
                                view.showToast(view.getActivity().getString(com.xtel.nipservicesdk.R.string.error_session_invalid));
                                view.requireLogin();
                            } else if (error != null && error.getCode() == 101) {
                                view.setProgressView("Không có dữ liệu");
                            } else {
                                if (error != null && error.getMessage() != null) {
                                    view.getDrugListError(error.getMessage());
                                }
                            }
                        }
                    });
                    break;


                case 2:
                    DrugAddEntity entity = new DrugAddEntity();
                    entity.setMrb_id((Integer) params[1]);
                    entity.setDrug_id((Integer) params[2]);
                    entity.setInstruction((String) params[3]);
                    MedicalDirectoryModel.getinstance().addDrugToMedicineReportBooks(entity, LoginManager.getCurrentSession(), new ResponseHandle<RESP_None>(RESP_None.class) {
                        @Override
                        public void onSuccess(RESP_None obj) {
                            view.addDrugSucces();
                        }

                        @Override
                        public void onError(Error error) {
                            view.addDrugError();
                        }
                    });
            }
        }
    };

    public DrugDetailsFragmentPresenter(IDrugDetailsFragment view) {
        this.view = view;
    }

    public void getListDrug(int id_medical_report_book) {
        if (!NetWorkInfo.isOnline(view.getActivity())) {
            view.showToast("Không có kết nối mạng.");
        } else {
            iCmd.excute(1, id_medical_report_book);
        }
    }

    public void addDrugToMedicineReport(int id_medicine_report_book, int id_medicine, String instruction) {
        if (!NetWorkInfo.isOnline(view.getActivity())) {
            view.showToast("Không có internet");
        } else {
            iCmd.excute(2, id_medicine_report_book, id_medicine, instruction);
        }
    }

}
