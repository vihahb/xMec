package com.xtelsolution.xmec.presenter;

import android.util.Log;

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
import com.xtelsolution.xmec.model.MedicalDirectoryModel;
import com.xtelsolution.xmec.model.REQ_Medical_Detail;
import com.xtelsolution.xmec.model.RESP_List_Disease_With_Link;
import com.xtelsolution.xmec.model.RESP_Medical_Detail;
import com.xtelsolution.xmec.model.Resource;
import com.xtelsolution.xmec.views.inf.IMedicalDetailView;

import java.util.List;

/**
 * Created by phimau on 3/6/2017.
 */

public class MedicalDetailPresenter extends BasePresenter {
    private static final String TAG = "MedicalDetailPresenter";
    private final int GETLISTILLNESS = 2;
    private final int GETDETAIlMEDICAL = 1;
    private final int UPDATEMEDICAL = 3;
    private final int REMOVEMEDiCAL = 4;
    private IMedicalDetailView view;

    public MedicalDetailPresenter(IMedicalDetailView view) {
        this.view = view;
    }

    private void getDetailMedical(final Object... param) {
        final int id = (int) param[1];
        Log.e("sdasdadasd", "getDetailMedical: " + id);
        String url = Constant.SERVER_XMEC + Constant.MEDICAL_REPORT_BOOK + "/" + id;
        MedicalDirectoryModel.getinstance().getMedicalDirectoryDetail(url, LoginManager.getCurrentSession(), new ResponseHandle<RESP_Medical_Detail>(RESP_Medical_Detail.class) {
            @Override
            public void onSuccess(RESP_Medical_Detail obj) {
                view.onLoadMedicalFinish(obj);
            }

            @Override
            public void onError(Error error) {
                if (error.getCode() == 2) {
                    CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                        @Override
                        public void onSuccess(RESP_Login success) {
                            getDetailMedical(GETDETAIlMEDICAL, id);
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

    private void getListIllness(final Object... param) {
        final int id = (int) param[1];
        String url = Constant.SERVER_XMEC + Constant.ILLNESS + "/" + id;
        xLog.e(TAG, "getListIllness: " + Constant.LOGPHI + "url" + url);
        DiseaseModel.getInstance().getListIllness(url, LoginManager.getCurrentSession(), new ResponseHandle<RESP_List_Disease_With_Link>(RESP_List_Disease_With_Link.class) {
            @Override
            public void onSuccess(RESP_List_Disease_With_Link obj) {
//                List<RESP_Disease> resp_diseases = obj.getData();
                view.onLoadListIllnessFinish(obj.getData());
            }

            @Override
            public void onError(Error error) {
                if (error.getCode() == 2) {
                    CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                        @Override
                        public void onSuccess(RESP_Login success) {
                            getListIllness(GETLISTILLNESS, id);
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

    //    int id,String name,long beginTime,long endTime,int type,String note,List<Resource> resources
    private void updateMedicalDirectory(final Object... param) {
//        RESP_Medical medical = (RESP_Medical) param[1];
        final int id = (int) param[1];
        final String name = (String) param[2];
        final long beginTime = (long) param[3];
        final long endTime = (long) param[4];
        final int type = (int) param[5];
        final String note = (String) param[6];
        final List<Resource> resources = (List<Resource>) param[7];
        view.showProgressDialog("Đang cập nhật");
        String url = Constant.SERVER_XMEC + Constant.MEDICAL_REPORT_BOOK;
        Log.e("ADD", "addMedicalDirectorry: " + url);
        REQ_Medical_Detail REQ_medicalDetail = new REQ_Medical_Detail();
        REQ_medicalDetail.setId(id);
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
        xLog.d(TAG, "getListIllness: " + "STRING" + "addMedicalDirectorry: " + JsonHelper.toJson(REQ_medicalDetail));
        MedicalDirectoryModel.getinstance().updateMedicalDirectory(url, JsonHelper.toJson(REQ_medicalDetail), LoginManager.getCurrentSession(), new ResponseHandle<RESP_Basic>(RESP_Basic.class) {
            @Override
            public void onSuccess(RESP_Basic obj) {
                view.onUpdateMedicalFinish();
                view.dismissProgressDialog();
            }

            @Override
            public void onError(Error error) {
                if (error.getCode() == 2) {
                    CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                        @Override
                        public void onSuccess(RESP_Login success) {
                            updateMedicalDirectory(UPDATEMEDICAL, id, name, beginTime, endTime, type, note, resources);
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

    public void removeMedical(final Object... param) {
        final int id = (int) param[1];
        view.showProgressDialog("Đang Xóa");
        String url = Constant.SERVER_XMEC + Constant.MEDICAL_REPORT_BOOK + "/" + id;
        MedicalDirectoryModel.getinstance().deleteMedicalDirectory(url, LoginManager.getCurrentSession(), new ResponseHandle<RESP_Basic>(RESP_Basic.class) {
            @Override
            public void onSuccess(RESP_Basic obj) {
                view.onRemoveMedicalSuccess();
                view.dismissProgressDialog();
            }

            @Override
            public void onError(Error error) {
                if (error.getCode() == 2) {
                    CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                        @Override
                        public void onSuccess(RESP_Login success) {
                            removeMedical(REMOVEMEDiCAL, id);
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

    public void checkRemoveMedical(int id) {
        if (!checkConnnecttion(view))
            return;
        removeMedical(REMOVEMEDiCAL, id);

    }

    public void checkGetListIllness(int id) {
        if (!checkConnnecttion(view))
            return;
        getListIllness(GETLISTILLNESS, id);
    }

    public void checkGetDetailMedical(int id) {
        if (!checkConnnecttion(view))
            return;
        getDetailMedical(GETDETAIlMEDICAL, id);
    }

    public void checkUpdateMedical(int id, String name, long beginTime, long endTime, int type, String note, List<Resource> resources) {
        if (!checkConnnecttion(view))
            return;
        updateMedicalDirectory(UPDATEMEDICAL, id, name, beginTime, endTime, type, note, resources);
    }
}
