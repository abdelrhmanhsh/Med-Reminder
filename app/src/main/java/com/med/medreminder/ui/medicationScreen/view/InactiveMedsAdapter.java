package com.med.medreminder.ui.medicationScreen.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.med.medreminder.R;

public class InactiveMedsAdapter extends RecyclerView.Adapter<InactiveMedsAdapter.ViewHolder>{

//    private Medicine[] medicines;
//    public InactiveMedsAdapter(Medicine[] medicines){
//        this.medicines = medicines;
//    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View row;
        TextView medName_txt;
        TextView medDose_txt;
        TextView medUnit_txt;
        ImageView med_img;


        public ViewHolder(@NonNull View convertView) {
            super(convertView);
            row = convertView;
            medName_txt = row.findViewById(R.id.medName_txt);
            medDose_txt = row.findViewById(R.id.medDose_txt);
            medUnit_txt = row.findViewById(R.id.medUnit_txt);
            med_img = row.findViewById(R.id.med_img);
        }

        public View getRow() { return row; }

        private TextView getMedName() { return medName_txt; }

        private TextView getMedDose() { return medDose_txt; }

        private TextView getMedUnit() { return medUnit_txt; }

        private ImageView getImg() { return med_img; }
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
        //replacement of data in view holder
//        holder.getMedName().setText(medicines[position].getName());
//        holder.getMedDose().setText(medicines[position].getDose());
//        holder.getMedUnit().setText(medicines[position].getQuantity());
//        holder.getImg().setImageResource(medicines[position].getImage());
    }

    @Override
    public int getItemCount() {
        return 0;
//        return medicines.length;
    }
}
