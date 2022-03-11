package com.med.medreminder.ui.medicationScreen.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.med.medreminder.R;
import com.med.medreminder.ui.medicationScreen.view.ActiveMedsAdapter;
import com.med.medreminder.ui.medicationScreen.view.InactiveMedsAdapter;
import com.med.medreminder.ui.medicationScreen.view.MedicationsScreen;

public class MedicationScreenFragment extends Fragment {
    private RecyclerView activeMeds;
    private RecyclerView inactiveMeds;
    private ActiveMedsAdapter activeAdapter;
    private InactiveMedsAdapter inactiveAdapter;
    //Medicine(int id, String name, String form, String strength, String reason, String isDaily, String often, String time,int image, int totalPills,String timeZone,String dose,String quantity) {

    private Button medBtn;


    public MedicationScreenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medication_screen, container, false);
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


}