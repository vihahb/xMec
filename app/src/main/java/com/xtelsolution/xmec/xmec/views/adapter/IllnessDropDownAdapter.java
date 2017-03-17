package com.xtelsolution.xmec.xmec.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.entity.Illness;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phimau on 2/28/2017.
 */

public class IllnessDropDownAdapter extends BaseAdapter implements Filterable {
    private ArrayList<Illness> mData;
    private ArrayList<Illness> mSuggestion;

    private Context mContext;

    public IllnessDropDownAdapter(Context mContext, ArrayList<Illness> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View mView = view;
        if (mView==null){
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            mView = inflater.inflate(R.layout.item_illness_dropdown,viewGroup,false);
            TextView tvName = (TextView) mView.findViewById(R.id.tv_name_illness);
            tvName.setText(mData.get(i).getName());
        }
        return  mView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return ((Illness) resultValue).getName();
            }

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
//                if (charSequence != null) {
//                    mDepartments_Suggestion.clear();
//                    for (Department department : mDepartments_All) {
//                        if (department.name.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
//                            mDepartments_Suggestion.add(department);
//                        }
//                    }
//                    FilterResults filterResults = new FilterResults();
//                    filterResults.values = mDepartments_Suggestion;
//                    filterResults.count = mDepartments_Suggestion.size();
//                    return filterResults;
//                } else {
//                    return new FilterResults();
//                }
                return  null;
            }

            @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            }
        };
    }
}
