package com.med.medreminder.ui.medicationScreen.view;

import android.content.Context;
import android.util.Log;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActiveMedsAdapter extends RecyclerView.Adapter<ActiveMedsAdapter.ViewHolder> {

    OnActiveMedClickListener onActiveMedClickListener;
    List<Medicine> medicines = new ArrayList<>();
    Context context;

    public ActiveMedsAdapter(OnActiveMedClickListener onActiveMedClickListener, Context context) {
        this.onActiveMedClickListener = onActiveMedClickListener;
        this.context = context;
    }


    public void setMedInfo(List<Medicine> medicines) {
        this.medicines = medicines;
        notifyDataSetChanged();
    }

    public void removeMeds(){
        medicines.clear();
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView medTime_txt;
        ImageView med_img;
        TextView medName_txt;
        TextView medStrength_txt;
        CardView med_card_active;
        TextView medNum_txt;


        public ViewHolder(@NonNull View convertView) {
            super(convertView);
            medTime_txt = itemView.findViewById(R.id.medTime_txt);
            med_card_active = itemView.findViewById(R.id.med_card_active);
            med_img = itemView.findViewById(R.id.med_img);
            medName_txt = itemView.findViewById(R.id.medName_txt);
            medStrength_txt = itemView.findViewById(R.id.medStrength_txt);
            medNum_txt = itemView.findViewById(R.id.medNum_txt);
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
            holder.medNum_txt.setText(medicines.get(position).getMedLeft()+"");

            int imgResource = medicines.get(position).getImage();
            int setImgResource;
            switch (imgResource){
                case 1:
                    setImgResource = R.drawable.ic_pill;
                    break;
                case 2:
                    setImgResource = R.drawable.ic_injection;
                    break;
                case 3:
                    setImgResource = R.drawable.ic_drops;
                    break;
                case 4:
                    setImgResource = R.drawable.ic_medicine_other;
                    break;
                default:
                    setImgResource = R.drawable.ic_medicine_other;
                    break;
            }

            Glide.with(context).load(setImgResource)
                    //.apply(new RequestOptions().override(200,200))
                    .placeholder(R.drawable.ic_medicine_other)
                    .error(R.drawable.ic_medicine_other)
                    .into(holder.med_img);

            holder.med_card_active.setOnClickListener(new View.OnClickListener() {
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
