package com.med.medreminder.ui.displayHelpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.med.medreminder.R;

import java.util.List;

public class DisplayHelperAdapter extends RecyclerView.Adapter<DisplayHelperAdapter.ViewHolder> {

    Context context;
    List<String> helperEmails;

    public DisplayHelperAdapter(Context context, List<String> helperEmails) {
        this.context = context;
        this.helperEmails = helperEmails;
    }

    public void setHelpers(List<String> helperEmails){
        this.helperEmails = helperEmails;
    }

    @NonNull
    @Override
    public DisplayHelperAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.helpers_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView user_img;
        TextView helperEmail_txt;
        CardView helpers_cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_img = itemView.findViewById(R.id.user_img);
            helperEmail_txt = itemView.findViewById(R.id.helperEmail_txt);
            helpers_cardView = itemView.findViewById(R.id.helpers_cardView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayHelperAdapter.ViewHolder holder, int position) {
        holder.helperEmail_txt.setText(helperEmails.get(position));

    }

    @Override
    public int getItemCount() {
        return helperEmails.size();
    }
}
