package com.xtelsolution.xmec.presenter;

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

public class HeathyCareDetailPresenter  {
    private IHeathyCareDetailView view;

    public HeathyCareDetailPresenter(IHeathyCareDetailView view) {
        this.view = view;
    }
    public void getHeathyCareDetail(int id){
        String url = Constant.SERVER_XMEC+Constant.HEALTHY_CENTER+"/"+id;
        HealthyCareModel.getInstance().getDetailHospital(url, LoginModel.getInstance().getSession(), new ResponseHandle<RESP_Healthy_Care_Detail>(RESP_Healthy_Care_Detail.class) {
            @Override
            public void onSuccess(RESP_Healthy_Care_Detail obj) {
                view.onGetHeathyCareSuccess(obj);
            }

            @Override
            public void onError(Error error) {
                xLog.e(error.getMessage());
            }
        });
    }

}
