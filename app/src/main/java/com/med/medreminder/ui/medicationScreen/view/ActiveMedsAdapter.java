package com.med.medreminder.ui.medicationScreen.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.med.medreminder.R;
import com.med.medreminder.model.Medicine;

import java.util.ArrayList;
import java.util.List;

public class ActiveMedsAdapter extends RecyclerView.Adapter<ActiveMedsAdapter.ViewHolder> {

    OnActiveMedClickListener onActiveMedClickListener;
    List<Medicine> medicines = new ArrayList<>();
    Context context;

    public ActiveMedsAdapter(OnActiveMedClickListener onActiveMedClickListener, Context context) {
        this.onActiveMedClickListener = onActiveMedClickListener;
      //  this.medicines = medicines;
        this.context = context;
    }


    public void setMedInfo(List<Medicine> medicines) {
        this.medicines = medicines;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView medTime_txt;
        ImageView med_img;
        TextView medName_txt;
        TextView medStrength_txt;
        TextView medForm_txt;
        CardView med_card;


        public ViewHolder(@NonNull View convertView) {
            super(convertView);
            medTime_txt = itemView.findViewById(R.id.medTime_txt);
            med_card = itemView.findViewById(R.id.med_card);
            med_img = itemView.findViewById(R.id.med_img);
            medName_txt = itemView.findViewById(R.id.medName_txt);
            medStrength_txt = itemView.findViewById(R.id.medStrength_txt);
            medForm_txt = itemView.findViewById(R.id.medForm_txt);
        }

    }

        @NonNull
        @Override
        public ActiveMedsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.active_meds,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ActiveMedsAdapter.ViewHolder holder, int position) {
            holder.medTime_txt.setText(medicines.get(position).getTime());
            holder.medName_txt.setText(medicines.get(position).getName());
            holder.medStrength_txt.setText(medicines.get(position).getStrength());
            holder.medForm_txt.setText(medicines.get(position).getForm());
            Glide.with(context).load(medicines.get(position).getImage())
                    //.apply(new RequestOptions().override(200,200))
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.med_img);

            holder.med_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onActiveMedClickListener.onActiveCLick(medicines.get(position));
                }
            });
        }

    @Override
    public int getItemCount() {
        return medicines.size();
    }

}
