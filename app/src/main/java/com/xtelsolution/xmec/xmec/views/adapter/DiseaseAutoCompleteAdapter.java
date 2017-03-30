package com.xtelsolution.xmec.xmec.views.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.commons.Cts;
import com.xtel.nipservicesdk.model.LoginModel;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.model.RESP_List_Disease;
import com.xtelsolution.xmec.model.entity.Disease;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by phimau on 3/17/2017.
 */

public class DiseaseAutoCompleteAdapter extends BaseAdapter implements Filterable {
    private static final String TAG = "DiseaseAutoCompleteAdapter";
    private static final int MAX_RESULTS = 10;
    private static final String url = Constant.SERVER_XMEC + Constant.Disease + "?name=";
    //    private
    private List<Disease> resultList = new ArrayList<Disease>();

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Object getItem(int i) {
        return resultList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return resultList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(android.R.layout.simple_dropdown_item_1line, viewGroup, false);

        }
        TextView tvName = (TextView) view.findViewById(android.R.id.text1);
        tvName.setText(resultList.get(i).getName());
        xLog.e(TAG, "getView: " + Constant.LOGPHI + "name" + resultList.get(i).getName() + "   " + i);
        return view;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                xLog.e(TAG, "getFilter: " + constraint.toString());
                List<Disease> diseases = new ArrayList<>();
                if (constraint != null && constraint.length() > 1) {
                    try {
                        xLog.e(TAG, "getFilter: " + Constant.LOGPHI + url + constraint.toString() + "&size=10");
                        diseases = new GetToServer().execute(url + constraint.toString() + "&size=10", LoginManager.getCurrentSession()).get();
                    } catch (InterruptedException e) {
                        xLog.e(TAG, "getFilter: " + e.getMessage());
                    } catch (ExecutionException e) {
                        xLog.e(TAG, "getFilter: " + e.getMessage());
                    }
                    filterResults.values = diseases;
                    filterResults.count = diseases.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                resultList.clear();
                if (results != null && results.count > 0) {
                    xLog.e(TAG, "getFilter: publishResults: " + Constant.LOGPHI + resultList.toString());
                    resultList.addAll((List<Disease>) results.values);
                    xLog.e(TAG, "getFilter: publishResults: " + Constant.LOGPHI + resultList.toString());
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }


    private class GetToServer extends AsyncTask<String, Integer, List<Disease>> {
        private boolean isSuccess = true;


        @Override
        protected List<Disease> doInBackground(String... params) {
            try {
                OkHttpClient client = new OkHttpClient();

                Request.Builder builder = new Request.Builder();
                builder.url(params[0]);

                if (params[1] != null)
                    builder.header(Cts.SESSION, params[1]);

                Request request = builder.build();

                Response response = client.newCall(request).execute();

                String jsonObjet = response.body().string();
                RESP_List_Disease t = null;
                try {
                    t = JsonHelper.getObjectNoException(jsonObjet, RESP_List_Disease.class);
                    xLog.e(TAG, "GetToServer: doInBackground: " + Constant.LOGPHI + "   " + t.getList().toString());
                    return t.getList();
                } catch (Exception e) {
                    xLog.e(TAG, "GetToServer: doInBackground: " + e.getMessage());
                }
                return null;
            } catch (IOException e) {
                xLog.e(TAG, "GetToServer: doInBackground: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Disease> s) {
            super.onPostExecute(s);
        }
    }
}
