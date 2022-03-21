package com.med.medreminder.ui.displayMedFriends;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.med.medreminder.R;
import com.med.medreminder.ui.homepage.view.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class DisplayMedFriendsAdapter extends RecyclerView.Adapter<DisplayMedFriendsAdapter.ViewHolder> {
    Context context;
    List<String> patientEmails = new ArrayList<>();
    OnClickListener onClickListener;

    public DisplayMedFriendsAdapter(Context context, List<String> patientEmails, OnClickListener onClickListener) {
        this.context = context;
        this.patientEmails = patientEmails;
        this.onClickListener = onClickListener;
    }

    public void setMedFriends(List<String> patientEmails){
        this.patientEmails = patientEmails;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView user_img;
        TextView senderEmail_txt;
        CardView medfriend_cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_img = itemView.findViewById(R.id.user_img);
            senderEmail_txt = itemView.findViewById(R.id.senderEmail_txt);
            medfriend_cardView = itemView.findViewById(R.id.medfriend_cv);
        }
    }

    @NonNull
    @Override
    public DisplayMedFriendsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.medfriend_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayMedFriendsAdapter.ViewHolder holder, int position) {
        holder.senderEmail_txt.setText(patientEmails.get(position));
        holder.medfriend_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onMedFriendClick(patientEmails.get(position).toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return patientEmails.size();
    }

}
