package com.xtelsolution.xmec.xmec.views.adapter;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.model.RESP_User;
import com.xtelsolution.xmec.model.SharedPreferencesUtils;

import java.text.DecimalFormat;
import java.util.List;

import agency.tango.android.avatarview.IImageLoader;
import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;

/**
 * Created by Admin on 5/23/2017.
 */

public class PagerUserAdapter extends RecyclerView.Adapter {
    private List<RESP_User> data;
    private Context mContext;
    IImageLoader imageLoader;
    private final int TYPE_ITEM = 0;
    private ItemUpDateClickListener itemUpDateClickListener;

    public PagerUserAdapter(Context mContext, List<RESP_User> data) {
        this.mContext = mContext;
        this.data = data;
        imageLoader = new PicassoLoader();
    }

    public void setItemUpDateClickListener(ItemUpDateClickListener itemUpDateClickListener) {
        this.itemUpDateClickListener = itemUpDateClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewpage_user, parent, false);
            return new UserViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewpage_add_user, parent, false);
            return new AddUserViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {

            UserViewHolder viewHolder = (UserViewHolder) holder;
            viewHolder.setData(position);
        } else {
            AddUserViewHolder addUserViewHolder = (AddUserViewHolder) holder;
            addUserViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "add thành viên", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    private class AddUserViewHolder extends RecyclerView.ViewHolder {
        LinearLayout add_layout;

        public AddUserViewHolder(View itemView) {
            super(itemView);
            add_layout = (LinearLayout) itemView.findViewById(R.id.add_layout);
        }
    }

    private class UserViewHolder extends RecyclerView.ViewHolder {

        private AvatarView imgAvatar;
        private TextView btnProfile;
        private TextView tvName;
        private TextView tvBirthday;
        private TextView tvHeight;
        private TextView tvWeight;
        private TextView tvIBM;
        private ImageView imgGender;
        private CoordinatorLayout progcess;

        private UserViewHolder(View view) {
            super(view);
            btnProfile = (TextView) view.findViewById(R.id.btnProfile);
            tvBirthday = (TextView) view.findViewById(R.id.tv_birthday);
            tvHeight = (TextView) view.findViewById(R.id.tv_profile_height);
            tvWeight = (TextView) view.findViewById(R.id.tv_profile_weight);
            tvIBM = (TextView) view.findViewById(R.id.tv_profile_ibm);
            tvName = (TextView) view.findViewById(R.id.tv_profile_name);
            imgAvatar = (AvatarView) view.findViewById(R.id.img_avatar);
            imgGender = (ImageView) view.findViewById(R.id.img_gender);
            progcess = (CoordinatorLayout) view.findViewById(R.id.progress_bar);
        }

        private void setData(final int position) {
            final RESP_User user = data.get(position);
            if (user == null) {

                return;
            }
            imageLoader.loadImage(imgAvatar, user.getAvatar(), user.getFullname());
            tvName.setText(user.getFullname());
            tvBirthday.setText(user.getBirthDayasString());
            tvHeight.setText(String.valueOf(user.getHeight()));
            tvWeight.setText(String.valueOf(user.getWeight()));
            try {
                DecimalFormat format = new DecimalFormat("##.##");
                tvIBM.setText(format.format(user.getWeight() * 100 * 100 / (user.getHeight() * user.getHeight())));
            } catch (Exception e) {
                tvIBM.setText("0.0");
            }


            if (user.getGender() == 2) {
//                imgGender.setVisibility(View.VISIBLE);
                imgGender.setImageResource(R.drawable.ic_action_name);
            } else if (user.getGender() == 1) {
//                imgGender.setVisibility(View.VISIBLE);
                imgGender.setImageResource(R.drawable.ic_man);
            } else {
                imgGender.setVisibility(View.GONE);
            }
            SharedPreferencesUtils.getInstance().saveUser(user);
            progcess.setVisibility(View.GONE);
            btnProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemUpDateClickListener != null)
                        itemUpDateClickListener.onClick(position, user);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position < data.size() ? TYPE_ITEM : 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(RESP_User user) {
        insert(user, data.size());
    }

    public void insert(RESP_User user, int position) {
        data.add(position, user);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        int size = data.size();
        data.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addAll(List<RESP_User> list) {
        int startIndex = data.size();
        data.addAll(startIndex, list);
        notifyItemRangeInserted(startIndex, list.size());
    }

    public interface ItemUpDateClickListener {
        void onClick(int position, RESP_User user);
    }
}