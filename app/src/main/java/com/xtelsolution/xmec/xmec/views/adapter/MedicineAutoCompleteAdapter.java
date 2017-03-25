package com.xtelsolution.xmec.xmec.views.adapter;

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
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.model.RESP_ID;
import com.xtelsolution.xmec.model.RESP_List_Disease;
import com.xtelsolution.xmec.model.RESP_List_Medicine;
import com.xtelsolution.xmec.model.RESP_Medicine;
import com.xtelsolution.xmec.model.entity.Disease;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by phimau on 3/19/2017.
 */

public class MedicineAutoCompleteAdapter extends BaseAdapter implements Filterable {
    private static final String url = Constant.SERVER_XMEC+Constant.MEDICINE_SEARCH+"?name=";
    private List<RESP_Medicine> resultList = new ArrayList<>();
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
        xLog.e(Constant.LOGPHI+"name"+resultList.get(i).getName()+"   "+i);
        return view;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                xLog.e(constraint.toString());
                List<RESP_Medicine> diseases = new ArrayList<>();
                if (constraint != null&&constraint.length()>1) {
                    try {
                        xLog.e(Constant.LOGPHI+url+constraint.toString()+"&size=10");
                        diseases = new MedicineAutoCompleteAdapter.GetToServer().execute(url+constraint.toString()+"&size=10", LoginManager.getCurrentSession()).get();
                    } catch (InterruptedException e) {
                        xLog.e(e.getMessage());
                    } catch (ExecutionException e) {
                        xLog.e(e.getMessage());
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
                    xLog.e(Constant.LOGPHI+resultList.toString());
                    resultList.addAll((List<RESP_Medicine>) results.values);
                    xLog.e(Constant.LOGPHI+resultList.toString());
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }


    private class GetToServer extends AsyncTask<String, Integer, List<RESP_Medicine>> {
        @Override
        protected List<RESP_Medicine> doInBackground(String... params) {
            try {
                OkHttpClient client = new OkHttpClient();

                Request.Builder builder = new Request.Builder();
                builder.url(params[0]);

                if (params[1] != null)
                    builder.header(Cts.SESSION, params[1]);

                Request request = builder.build();

                Response response = client.newCall(request).execute();

                String jsonObjet = response.body().string();
                RESP_List_Medicine t = null;
                try {
                    t = JsonHelper.getObjectNoException(jsonObjet, RESP_List_Medicine.class);
                    xLog.e(Constant.LOGPHI+"   "+t.getData().toString());
                    return t.getData();
                } catch (Exception e) {
                    xLog.e(e.getMessage());
                }
                return null;
            } catch (IOException e) {
                xLog.e(e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<RESP_Medicine> s) {
            super.onPostExecute(s);
        }
    }
}

