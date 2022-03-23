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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InactiveMedsAdapter extends RecyclerView.Adapter<InactiveMedsAdapter.ViewHolder>{

    OnInactiveMedClickListener onInactiveMedClickListener;
    List<Medicine> medicineList = new ArrayList<>();
    Context context;

    public InactiveMedsAdapter(OnInactiveMedClickListener onInactiveMedClickListener, Context context) {
        this.onInactiveMedClickListener = onInactiveMedClickListener;
       // this.medicineList = medicineList;
        this.context = context;
    }

    public void removeMeds(){
        medicineList.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View row;
        TextView medName_txt;
        TextView medStrength_txt;
        ImageView med_img;
        CardView med_card_inactive;


        public ViewHolder(@NonNull View convertView) {
            super(convertView);
            row = convertView;
            medName_txt = row.findViewById(R.id.medName_txt);
            medStrength_txt = row.findViewById(R.id.medStrength_txt);
            med_img = row.findViewById(R.id.med_img);
            med_card_inactive = row.findViewById(R.id.med_card_inactive);
        }
    }

    @NonNull
    @Override
    public InactiveMedsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.inactive_meds,parent,false);
        InactiveMedsAdapter.ViewHolder viewHolder = new InactiveMedsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InactiveMedsAdapter.ViewHolder holder, int position) {
        holder.medName_txt.setText(medicineList.get(position).getName());
        holder.medStrength_txt.setText(medicineList.get(position).getStrength());
        Glide.with(context).load(medicineList.get(position).getImage())
                //.apply(new RequestOptions().override(200,200))
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.med_img);
        holder.med_card_inactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onInactiveMedClickListener.onInactiveClick(medicineList.get(position));
            }
        });
    }

    public void setInactiveMedInfo(List<Medicine> medicineList) {
        this.medicineList=medicineList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return medicineList.size();
    }
}
