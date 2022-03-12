package com.med.medreminder.ui.homepage.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.med.medreminder.R;
import com.med.medreminder.databinding.FragmentDashboardBinding;
import com.med.medreminder.model.Medicine;
import com.med.medreminder.ui.medicationScreen.view.ActiveMedsAdapter;
import com.med.medreminder.ui.medicationScreen.view.InactiveMedsAdapter;
import com.med.medreminder.ui.medicationScreen.view.OnActiveMedClickListener;
import com.med.medreminder.ui.medicationScreen.view.OnInactiveMedClickListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class DashboardFragment extends Fragment implements OnInactiveMedClickListener, OnActiveMedClickListener {

    private RecyclerView activeMeds;
    private RecyclerView inactiveMeds;
    private ActiveMedsAdapter activeAdapter;
    private InactiveMedsAdapter inactiveAdapter;
    private Button medBtn;


    private FragmentDashboardBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activeMeds = view.findViewById(R.id.recyclerView_ActiveMeds);
        inactiveMeds = view.findViewById(R.id.recyclerView_InctiveMeds);
        medBtn = view.findViewById(R.id.medBtn);
//        activeAdapter = new ActiveMedsAdapter(MedicationsScreen.active_meds);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        activeMeds.setLayoutManager(layoutManager);
        activeMeds.setAdapter(activeAdapter);

//        inactiveAdapter = new InactiveMedsAdapter(MedicationsScreen.inactive_meds);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        inactiveMeds.setLayoutManager(layoutManager2);
        inactiveMeds.setAdapter(inactiveAdapter);

        medBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Medication Drug Screen
                //Intent intent = new Intent(MedicationScreenFragment.this, MedicationDrugScreen.class);
                //startActivity(intent);
            }
        });

    }


    @Override
    public void onCLick(Medicine medicine) {
    }

    @Override
    public void onActiveCLick(Medicine medicine) {

    }
}