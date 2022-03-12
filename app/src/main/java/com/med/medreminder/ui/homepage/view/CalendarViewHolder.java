package com.med.medreminder.ui.homepage.view;

import android.view.View;
import android.widget.TextView;

import com.med.medreminder.R;

import java.time.LocalDate;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private final ArrayList<LocalDate> days;
    public final View parentView;
    public final TextView dayOfMonth;
    private final CalendarHomeAdapter.OnItemListener onItemListener;
    public CalendarViewHolder(@NonNull View itemView, CalendarHomeAdapter.OnItemListener onItemListener, ArrayList<LocalDate> days)
    {
        super(itemView);
        parentView = itemView.findViewById(R.id.parentView);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
        this.days = days;
    }

    @Override
    public void onClick(View view)
    {
        onItemListener.onItemClick(getAdapterPosition(), days.get(getAdapterPosition()));
    }
}
