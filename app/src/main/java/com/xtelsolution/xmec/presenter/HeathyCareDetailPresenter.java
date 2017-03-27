package com.xtelsolution.xmec.presenter;

import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.LoginModel;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.model.HealthyCareModel;
import com.xtelsolution.xmec.model.RESP_Healthy_Care_Detail;
import com.xtelsolution.xmec.xmec.views.inf.IHeathyCareDetailView;

/**
 * Created by phimau on 3/16/2017.
 */

public class HeathyCareDetailPresenter extends BasePresenter  {
    private IHeathyCareDetailView view;
    private final int GETHEALTHCAREDETAIl =1;
    public HeathyCareDetailPresenter(IHeathyCareDetailView view) {
        this.view = view;
    }
    private void getHeathyCareDetail(final Object...param){
        int id = (int) param[1];
        String url = Constant.SERVER_XMEC+Constant.HEALTHY_CENTER+"/"+id;
        xLog.e( url);
        HealthyCareModel.getInstance().getDetailHospital(url, LoginManager.getCurrentSession(), new ResponseHandle<RESP_Healthy_Care_Detail>(RESP_Healthy_Care_Detail.class) {
            @Override
            public void onSuccess(RESP_Healthy_Care_Detail obj) {
                view.onGetHeathyCareSuccess(obj);
            }

            @Override
            public void onError(Error error) {
                handlerError(view,error,param);
            }
        });
    }

    public void checkGetHealthCare(int id){
        if (!checkConnnecttion(view))
            return;
        getHeathyCareDetail(GETHEALTHCAREDETAIl,id);
    }

    @Override
    public void onGetNewSessionSuccess(Object... param) {
        switch ((int)param[0]){
            case 1:
                getHeathyCareDetail(param);
                break;
        }
    }
}
