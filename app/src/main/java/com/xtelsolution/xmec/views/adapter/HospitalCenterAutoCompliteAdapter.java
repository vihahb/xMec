package com.xtelsolution.xmec.views.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.commons.Cts;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.model.RESP_List_Map_Healthy_Care;
import com.xtelsolution.xmec.model.RESP_Map_Healthy_Care;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by phimau on 7/18/2017.
 */

public class HospitalCenterAutoCompliteAdapter extends BaseAdapter implements Filterable {

    private static final String url = Constant.SERVER_XMEC + Constant.HEALTHY_CENTER_HOME + "name?key=";
    private static final String TAG = "HospitalCenterAutoCompliteAdapter";

    private List<RESP_Map_Healthy_Care> healthyCares = new ArrayList<>();
    private Context mContext;

    public HospitalCenterAutoCompliteAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return healthyCares.size();
    }

    @Override
    public Object getItem(int position) {
        return healthyCares.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        view = inflater.inflate(android.R.layout.simple_dropdown_item_1line, viewGroup, false);
        TextView tvName = (TextView) view.findViewById(android.R.id.text1);
        tvName.setText(healthyCares.get(position).getName());
        return view;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                xLog.e(TAG, "getFilter: " + constraint.toString());
                List<RESP_Map_Healthy_Care> diseases = new ArrayList<>();
                if (constraint != null && constraint.length() > 1) {
                    try {
                        xLog.e(TAG, "getFilter: " + Constant.LOGPHI + url + constraint.toString() + "&size=10");
                        diseases = new GetToServer().execute(url + constraint.toString() + "&size=10", Constant.LOCAL_SECCION).get();
                    } catch (InterruptedException e) {
                        xLog.e(TAG, "getFilter: " + e.getMessage());
                    } catch (ExecutionException e) {
                        xLog.e(TAG, "getFilter: " + e.getMessage());
                    }
                    filterResults.values = diseases;
                    filterResults.count = diseases.size();
                    if (diseases.size()==0)
                        Toast.makeText(mContext, R.string.map_no_search_resutl, Toast.LENGTH_SHORT).show();

                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                healthyCares.clear();
                if (results != null && results.count > 0) {
                    xLog.e(TAG, "getFilter: publishResults: " + Constant.LOGPHI + healthyCares.toString());
                    healthyCares.addAll((List<RESP_Map_Healthy_Care>) results.values);
                    xLog.e(TAG, "getFilter: publishResults: " + Constant.LOGPHI + healthyCares.toString());
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }
    private class GetToServer extends AsyncTask<String, Integer, List<RESP_Map_Healthy_Care>> {
        private boolean isSuccess = true;


        @Override
        protected List<RESP_Map_Healthy_Care> doInBackground(String... params) {
            try {
                OkHttpClient client = new OkHttpClient();

                Request.Builder builder = new Request.Builder();
                builder.url(params[0]);

                if (params[1] != null)
                    builder.header(Cts.SESSION, params[1]);

                Request request = builder.build();

                Response response = client.newCall(request).execute();

                String jsonObjet = response.body().string();
                RESP_List_Map_Healthy_Care t = null;
                try {
                    t = JsonHelper.getObjectNoException(jsonObjet, RESP_List_Map_Healthy_Care.class);
                    xLog.e(TAG, "GetToServer: doInBackground: " + Constant.LOGPHI + "   " + t.getData().toString());
                    return t.getData();
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
        protected void onPostExecute(List<RESP_Map_Healthy_Care> s) {
            super.onPostExecute(s);
        }
    }
}
