package com.med.medreminder.ui.request.view;

import static com.med.medreminder.ui.request.view.RequestsActivity.currUserEmail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.med.medreminder.R;

import java.util.ArrayList;
import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    Context context;
    List<String> emails = new ArrayList<String>();


    public RequestAdapter(List<String> emails,Context context) {
        this.context = context;
        this.emails = emails;
    }


    public void setRequests(List<String> emails){
        this.emails = emails;
    }


    @NonNull
    @Override
    public RequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.request_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


     class ViewHolder extends RecyclerView.ViewHolder {
        ImageView user_img;
        TextView senderEmail_txt;
        Button accept_btn;
        Button decline_btn;
        CardView request_cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_img = itemView.findViewById(R.id.user_img);
            accept_btn = itemView.findViewById(R.id.accept_btn);
            decline_btn = itemView.findViewById(R.id.decline_btn);
            senderEmail_txt = itemView.findViewById(R.id.senderEmail_txt);
            request_cardView = itemView.findViewById(R.id.request_cardView);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RequestAdapter.ViewHolder holder, int position) {
        holder.senderEmail_txt.setText(emails.get(position));
        holder.accept_btn.setTag(position);
        holder.accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String STATUS = "ACCEPTED";
                holder.request_cardView.setVisibility(View.GONE);
                RequestsActivity r = new RequestsActivity();
                r.updateStatusInFirestore(currUserEmail,holder.senderEmail_txt.getText().toString(),STATUS);
            }
        });

        holder.decline_btn.setTag(position);
        holder.decline_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String STATUS = "DECLINED";
                holder.request_cardView.setVisibility(View.GONE);
                RequestsActivity r = new RequestsActivity();
                r.updateStatusInFirestore(currUserEmail,holder.senderEmail_txt.getText().toString(),STATUS);
            }
        });
    }

    @Override
    public int getItemCount() {
        return emails.size();
    }




}
