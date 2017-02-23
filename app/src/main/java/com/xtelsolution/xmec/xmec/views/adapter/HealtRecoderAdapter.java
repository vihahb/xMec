package com.xtelsolution.xmec.xmec.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.xmec.views.activity.BasicActivity;

import java.util.List;

/**
 * Created by phimau on 1/22/2017.
 */

public class HealtRecoderAdapter  extends RecyclerView.Adapter<HealtRecoderAdapter.HealthRecoderViewHoder> {
    private List<String> urlList;
    private Context context;

    public HealtRecoderAdapter(Context context,List<String> list) {
        this.context = context;
        this.urlList =list;
    }

    @Override
    public HealthRecoderViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_health_recoder,null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return  new HealthRecoderViewHoder(view);
    }

    @Override
    public void onBindViewHolder(HealthRecoderViewHoder holder, int position) {
        Picasso.with(context)
                .load(urlList.get(position))
                .placeholder(R.drawable.image_director)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return urlList.size();
    }

    class HealthRecoderViewHoder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public HealthRecoderViewHoder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img_health_recoder);
        }
    }
    public void add(String url){
        urlList.add(url);
        notifyDataSetChanged();
    }
    public void addAll(List<String> urls){
        urlList.addAll(urls);
        notifyDataSetChanged();
    }
}
