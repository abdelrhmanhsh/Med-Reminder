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
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MedHomeAdapter extends RecyclerView.Adapter<MedHomeAdapter.ViewHolder> {
    private Context context;
    private List<Medicine> medicines = new ArrayList<>();
    private onMedClickListener onMedClickListener;

    public MedHomeAdapter(Context context, com.med.medreminder.ui.homepage.view.onMedClickListener onMedClickListener) {
        this.context = context;
        this.onMedClickListener = onMedClickListener;
    }

    public void setMedInfo(List<Medicine> medicines) {
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
          holder.medName_txt.setText(medicines.get(position).getName());
          holder.medStrength_txt.setText(medicines.get(position).getStrength());
          holder.medForm_txt.setText(medicines.get(position).getForm());
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
        CardView med_card;
        ImageView med_img;
        TextView medName_txt;
        TextView medStrength_txt;
        TextView medForm_txt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            medTime_txt = itemView.findViewById(R.id.medTime_txt);
            med_card = itemView.findViewById(R.id.med_card);
            med_img = itemView.findViewById(R.id.med_img);
            medName_txt = itemView.findViewById(R.id.medName_txt);
            medStrength_txt = itemView.findViewById(R.id.medStrength_txt);
            medForm_txt = itemView.findViewById(R.id.medForm_txt);
        }
    }
}
