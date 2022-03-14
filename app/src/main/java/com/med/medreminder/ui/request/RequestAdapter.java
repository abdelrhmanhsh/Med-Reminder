package com.med.medreminder.ui.request;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.med.medreminder.R;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {
    @NonNull
    @Override
    public RequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.request_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        ImageView user_img;
        TextView userName_txt;
        Button accept_btn;
        Button decline_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_img = itemView.findViewById(R.id.user_img);
            userName_txt = itemView.findViewById(R.id.userName_txt);
            accept_btn = itemView.findViewById(R.id.accept_btn);
            decline_btn = itemView.findViewById(R.id.decline_btn);
        }
    }
}
