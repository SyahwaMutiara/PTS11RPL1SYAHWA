package com.example.pts11rpl1syahwa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ListClubAdapter extends RecyclerView.Adapter<ListClubAdapter.ListViewHolder> {
    private List<ClubModel> listClub;
    private Callback callback;

    public ListClubAdapter(List<ClubModel> list, Callback callback) {
        this.listClub = list;
        this.callback = callback;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_club, parent, false);
        return new ListViewHolder(view);
    }

    interface Callback {
        void onClick(int position);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
     ClubModel club = listClub.get(position);
        Glide.with(holder.itemView.getContext())
                .load(club.getGambar())
                .apply(new RequestOptions().override(55,55))
                .into(holder.imgPhoto);
        holder.tvName.setText(club.getNama());
        holder.tvDetail.setText(club.getDeskripsi());
    }

    @Override
    public int getItemCount() {
        return listClub.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvName;
        TextView tvDetail;
        LinearLayout linearLayout;

        ListViewHolder(View iteamview) {
            super(iteamview);
            imgPhoto = iteamview.findViewById(R.id.img_item_club);
            tvName = iteamview.findViewById(R.id.tv_item_name);
            tvDetail = iteamview.findViewById(R.id.tv_detail);
            linearLayout = iteamview.findViewById(R.id.layout_rv);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onClick(getAdapterPosition());
                }
            });
        }
    }
}
