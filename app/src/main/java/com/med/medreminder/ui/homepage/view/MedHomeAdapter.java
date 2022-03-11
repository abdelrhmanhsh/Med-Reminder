package com.med.medreminder.ui.homepage.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.med.medreminder.R;
import com.med.medreminder.model.Medicine;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MedHomeAdapter extends RecyclerView.Adapter<MedHomeAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Medicine> medicines = new ArrayList<>();
    private onMedClickListener onMedClickListener;

    public MedHomeAdapter(Context context, com.med.medreminder.ui.homepage.view.onMedClickListener onMedClickListener) {
        this.context = context;
        this.onMedClickListener = onMedClickListener;
    }

    public void setMedInfo(ArrayList<Medicine> medicines) {
        this.medicines = medicines;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_medicine, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          holder.medTime_txt.setText(medicines.get(position).getTime());
//          holder.timeZone_txt.setText(medicines.get(position).getTimeZone());
          holder.medName_txt.setText(medicines.get(position).getName());
          holder.medName_txt.setText(medicines.get(position).getName());
//          holder.medUnit_txt.setText(medicines.get(position).getUnit());
//          holder.pillNum_txt.setText(medicines.get(position).getQuantity());
          holder.pill_txt.setText(medicines.get(position).getForm());
        Glide.with(context).load(medicines.get(position).getImage())
                //.apply(new RequestOptions().override(200,200))
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.med_img);

        holder.med_card.setOnClickListener(view -> {
            onMedClickListener.onCLick(medicines.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView medTime_txt;
        TextView timeZone_txt;
        CardView med_card;
        ImageView med_img;
        TextView medName_txt;
        TextView medDose_txt;
        TextView medUnit_txt;
        TextView pillNum_txt;
        TextView pill_txt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            medTime_txt = itemView.findViewById(R.id.medTime_txt);
            timeZone_txt = itemView.findViewById(R.id.timeZone_txt);
            med_card = itemView.findViewById(R.id.med_card);
            med_img = itemView.findViewById(R.id.med_img);
            medName_txt = itemView.findViewById(R.id.medName_txt);
            medDose_txt = itemView.findViewById(R.id.medDose_txt);
            medUnit_txt = itemView.findViewById(R.id.medUnit_txt);
            pillNum_txt = itemView.findViewById(R.id.pillNum_txt);
            pill_txt = itemView.findViewById(R.id.pill_txt);
        }
    }
}
