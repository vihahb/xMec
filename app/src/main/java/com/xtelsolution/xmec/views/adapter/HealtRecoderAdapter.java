package com.xtelsolution.xmec.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.Resource;

import java.util.List;

/**
 * Created by phimau on 1/22/2017.
 */

public class HealtRecoderAdapter extends RecyclerView.Adapter<HealtRecoderAdapter.HealthRecoderViewHoder> {
    private List<Resource> urlList;
    private Context context;
    private ItemClickListener itemClickListener;

    public HealtRecoderAdapter(Context context, List<Resource> list) {
        this.context = context;
        this.urlList = list;
    }

    @Override
    public HealthRecoderViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_health_recoder, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new HealthRecoderViewHoder(view);
    }

    @Override
    public void onBindViewHolder(final HealthRecoderViewHoder holder, final int position) {
//        Ion.with(context)
//                .load(urlList.get(position).getServer_path())
//                .intoImageView(holder.imageView)
//                .setCallback(new FutureCallback<ImageView>() {
//                    @Override
//                    public void onCompleted(Exception e, ImageView result) {
//
//                    }
//                });
        Picasso.with(context)
                .load(urlList.get(position).getServer_path())
                .placeholder(R.drawable.image_director)
                .into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClickListener(urlList.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return urlList.size();
    }

    public void add(Resource resource) {
        urlList.add(resource);
        notifyDataSetChanged();
    }

    public void addAll(List<Resource> resources) {
        urlList.addAll(resources);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;

    }

    class HealthRecoderViewHoder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public HealthRecoderViewHoder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img_health_recoder);
        }
    }

}
