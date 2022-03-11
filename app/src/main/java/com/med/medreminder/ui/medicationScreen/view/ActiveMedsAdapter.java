package com.med.medreminder.ui.medicationScreen.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.med.medreminder.R;

public class ActiveMedsAdapter extends RecyclerView.Adapter<ActiveMedsAdapter.ViewHolder> {

//    Medicine[] medicines;
//    public ActiveMedsAdapter(Medicine[] medicines){
//        this.medicines = medicines;
//    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View row;
        TextView medTime_txt;
        TextView timeZone_txt;
        TextView medName_txt;
        TextView medDose_txt;
        TextView medUnit_txt;
        ImageView med_img;
        TextView pillNum_txt;


        public ViewHolder(@NonNull View convertView) {
            super(convertView);
            row = convertView;
            medTime_txt = row.findViewById(R.id.medTime_txt);
            timeZone_txt = row.findViewById(R.id.timeZone_txt);
            medName_txt = row.findViewById(R.id.medName_txt);
            medDose_txt = row.findViewById(R.id.medDose_txt);
            medUnit_txt = row.findViewById(R.id.medUnit_txt);
            med_img = row.findViewById(R.id.med_img);
            pillNum_txt = row.findViewById(R.id.pillNum_txt);
        }
        public View getRow() {
            return row;
        }
        private TextView getMedTime() { return medTime_txt; }

        private TextView getTimeZone() { return timeZone_txt; }

        private TextView getMedName() { return medName_txt; }

        private TextView getMedDose() { return medDose_txt; }

        private TextView getMedUnit() { return medUnit_txt; }

        private ImageView getImg() { return med_img; }

        private TextView getPillNum() {return pillNum_txt; }

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
            //replacement of data in view holder
//            holder.getMedTime().setText(medicines[position].getTime());
//            holder.getMedName().setText(medicines[position].getName());
//            holder.getMedDose().setText(medicines[position].getStrength());
//            holder.getImg().setImageResource(medicines[position].getImage());
//            holder.getPillNum().setText(medicines[position].getRefillLimit()+"");
        }

    @Override
    public int getItemCount() {
        return 0;
//        return medicines.length;
    }

}
