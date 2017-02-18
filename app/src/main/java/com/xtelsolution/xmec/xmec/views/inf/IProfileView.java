package com.xtelsolution.xmec.xmec.views.inf;

/**
 * Created by phimau on 2/18/2017.
 */

public interface IProfileView extends BaseView{
    void onLoadProfileSuccess(String name, long birthday,double height,double weight,String url);
    void onUpdateProfileSuccess();
}
