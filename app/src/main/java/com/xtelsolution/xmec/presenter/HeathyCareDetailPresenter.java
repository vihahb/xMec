package com.xtelsolution.xmec.presenter;

import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtelsolution.xmec.callbacks.HtmlLoader;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.listener.LoadHtmlDetailListener;
import com.xtelsolution.xmec.model.HealthyCareModel;
import com.xtelsolution.xmec.model.RESP_Healthy_Care_Detail;
import com.xtelsolution.xmec.views.inf.IHeathyCareDetailView;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by phimau on 3/16/2017.
 */

public class HeathyCareDetailPresenter extends BasePresenter {
    private static final String TAG = "HeathyCareDetailPresenter";
    private final int GETHEALTHCAREDETAIl = 1;
    private IHeathyCareDetailView view;

    public HeathyCareDetailPresenter(IHeathyCareDetailView view) {
        this.view = view;
    }

    private void getHeathyCareDetail(final Object... param) {
        int id = (int) param[1];
        String url = Constant.SERVER_XMEC + Constant.HEALTHY_CENTER + "/" + id;
        xLog.e(TAG, "getHeathyCareDetail: " + url);
        HealthyCareModel.getInstance().getDetailHospital(url, getSession(), new ResponseHandle<RESP_Healthy_Care_Detail>(RESP_Healthy_Care_Detail.class) {
            @Override
            public void onSuccess(RESP_Healthy_Care_Detail obj) {
                view.onGetHeathyCareSuccess(obj);
                getDetail(obj.getUrl());
            }

            @Override
            public void onError(Error error) {
                handlerError(view, error, param);
            }
        });
    }


    public void getDetail(String url) {
        new HtmlLoader(new LoadHtmlDetailListener() {
            @Override
            public void onPrepare() {

            }

            @Override
            public void onSucess(Document result) {
                String openTime = "";
                String description = "";
                String phone = "";
                String urlAvavtar = "";
                /// lay opentime
                try {
                    Element s = result.select("ul.opening-hours").first();
                    Elements elements = s.children();
                    for (int i = 0; i < elements.size(); i++) {
                        openTime = openTime + elements.get(i).ownText();
                        if (i != elements.size() - 1)
                            openTime += "\n";
                        xLog.e("hiahiaahiahaaiahai", "kekek");

                    }
                    xLog.e("openTime", openTime);
                } catch (Exception e) {
                    openTime = null;
                }

                ///lay mo ta co so y te
                try {
                    Element subsection = result.select("div.subsection").first();
                    Elements contents = subsection.child(0).children();
                    for (int i = 0; i < contents.size(); i++) {
                        description += contents.get(i).ownText();
                        if (i != contents.size())
                            description += "\n \n";
                    }
                    xLog.e("descripton", description);

                } catch (Exception e) {
                    description = null;
                }

                /// Lay so dien thoai
                try {
                    Element phoneElement = result.select("a.phone").first();
                    phone = phoneElement.ownText();
                    xLog.e("phone", phone);
                } catch (Exception e) {
                    phone = null;
                }
                try {
                    Element avavtar = result.select("div#hero-image").first();
                    Element item = avavtar.child(0);
                    Element itemitem = item.child(0);
                    xLog.e("style", avavtar.outerHtml());
                    String url = itemitem.attr("style");
                    urlAvavtar = url.substring(url.indexOf("http"), url.indexOf(");"));
                } catch (Exception e) {
                    urlAvavtar = null;
                }
                view.onGetHealCareSuccess(openTime, description, phone, urlAvavtar);
                view.dismissProgressDialog();
            }

            @Override
            public void onError() {

            }
        }).execute(url);
    }

    public void checkGetHealthCare(int id) {
        if (!checkConnnecttion(view))
            return;
        view.showProgressDialog("Đang tải");
        getHeathyCareDetail(GETHEALTHCAREDETAIl, id);
    }

    @Override
    public void onGetNewSessionSuccess(Object... param) {
        switch ((int) param[0]) {
            case 1:
                getHeathyCareDetail(param);
                break;
        }
    }
}
