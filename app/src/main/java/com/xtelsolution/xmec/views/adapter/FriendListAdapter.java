package com.xtelsolution.xmec.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.model.entity.UserInfo;
import com.xtelsolution.xmec.views.activity.inf.INotificationActivity;

import java.util.ArrayList;

/**
 * Created by vivhp on 7/27/2017
 */

public class FriendListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<UserInfo> arrayList;
    private int ITEM_FRIEND_HEADER = 11;
    private int ITEM_FRIEND_REQUEST = 22;
    private int ITEM_FRIEND_ACCEPTED = 33;
    private INotificationActivity view;
    private Context mContext;

    public FriendListAdapter(Context context, ArrayList<UserInfo> arrayList, INotificationActivity view) {
        this.arrayList = arrayList;
        this.mContext = context;
        this.view = view;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_FRIEND_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_header, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == ITEM_FRIEND_REQUEST) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_friend_pending, parent, false);
            return new FriendViewHolder(view);
        } else if (viewType == ITEM_FRIEND_ACCEPTED) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_friend_accepted, parent, false);
            return new AcceptedViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        UserInfo user = arrayList.get(position);

        if (holder instanceof FriendViewHolder) {

            FriendViewHolder friendViewHolder = (FriendViewHolder) holder;
            friendViewHolder.tv_friend_name.setText(user.getFullname());
            friendViewHolder.setImage(user.getAvatar());
            friendViewHolder.setAction(user);
        } else if (holder instanceof HeaderViewHolder) {

            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.tv_title_header.setText(user.getFullname());

        } else if (holder instanceof AcceptedViewHolder) {

            AcceptedViewHolder acceptedViewHolder = (AcceptedViewHolder) holder;
            String result = "<b>" + user.getFullname() + "</b>" + " đã trở thành bạn bè với bạn.";
            acceptedViewHolder.tv_friend_name.setText(Html.fromHtml(result));
            acceptedViewHolder.setImage(user.getAvatar());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (arrayList.get(position).isHeader()) {
            return ITEM_FRIEND_HEADER;
        } else if (arrayList.get(position).isAccepted()) {
            return ITEM_FRIEND_ACCEPTED;
        } else {
            return ITEM_FRIEND_REQUEST;
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setAcceptedItem(int uid) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getUid() == uid) {
                arrayList.get(i).setAccepted(true);
            }
        }
        notifyDataSetChanged();
    }

    public void setRemoveItem(int uid) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getUid() == uid) {
                arrayList.remove(i);
            }
        }
        notifyDataSetChanged();
    }

    private class FriendViewHolder extends RecyclerView.ViewHolder {

        private Button btnAccept, btnDecline;
        private TextView tv_friend_name;
        private ImageView img_avatar;

        FriendViewHolder(View itemView) {
            super(itemView);
            btnAccept = (Button) itemView.findViewById(R.id.btn_friend_accept);
            btnDecline = (Button) itemView.findViewById(R.id.btn_friend_decline);
            tv_friend_name = (TextView) itemView.findViewById(R.id.tv_friend_name);
            img_avatar = (ImageView) itemView.findViewById(R.id.img_friend_avatar);
        }

        public void setImage(String url) {
            Picasso.with(mContext)
                    .load(url)
                    .fit().centerInside()
                    .error(R.mipmap.ic_user_default)
                    .into(img_avatar);
        }

        public void setAction(final UserInfo info) {
            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.onAccept(info.getUid());
                }
            });

            btnDecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.onDecline(info.getUid());
                }
            });
        }

    }

    private class AcceptedViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_friend_name;
        private ImageView img_avatar;

        AcceptedViewHolder(View itemView) {
            super(itemView);
            tv_friend_name = (TextView) itemView.findViewById(R.id.tv_friend_name);
            img_avatar = (ImageView) itemView.findViewById(R.id.img_friend_avatar);
        }

        public void setImage(String url) {
            Picasso.with(mContext)
                    .load(url)
                    .error(R.mipmap.ic_user_default)
                    .into(img_avatar);
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title_header;

        HeaderViewHolder(View itemView) {
            super(itemView);
            tv_title_header = (TextView) itemView.findViewById(R.id.tv_title_header);
        }

        public void setTitle(String mes) {
            if (TextUtils.isEmpty(mes)) {
                tv_title_header.setText(mes);
            }
        }
    }

}
