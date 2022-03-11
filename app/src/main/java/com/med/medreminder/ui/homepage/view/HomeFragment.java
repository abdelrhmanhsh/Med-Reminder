package com.med.medreminder.ui.homepage.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.med.medreminder.R;
import com.med.medreminder.databinding.FragmentHomeBinding;
import com.med.medreminder.model.Medicine;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class HomeFragment extends Fragment implements onMedClickListener{

    private FragmentHomeBinding binding;
    LinearLayoutManager linearLayoutManager;
    MedHomeAdapter medHomeAdapter;
    RecyclerView allMed_rv;
    FloatingActionButton addMed_floatBtn;
    ArrayList<Medicine> medicines;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        allMed_rv = view.findViewById(R.id.allMed_rv);
        addMed_floatBtn = view.findViewById(R.id.addMed_floatBtn);

        medicines = new ArrayList<>();

//        medicines.add(new Medicine(R.drawable.pill_ic,"Vitamic C","Pill(s)","250","10:00","AM","gm","1"));
//        medicines.add(new Medicine(R.drawable.pill_ic,"Vitamic C","Pill(s)","250","10:00","AM","gm","1"));
//        medicines.add(new Medicine(R.drawable.pill_ic,"Vitamic C","Pill(s)","250","10:00","AM","gm","1"));
//        medicines.add(new Medicine(R.drawable.pill_ic,"Vitamic C","Pill(s)","250","10:00","AM","gm","1"));
//        medicines.add(new Medicine(R.drawable.pill_ic,"Vitamic C","Pill(s)","250","10:00","AM","gm","1"));

        allMed_rv.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        allMed_rv.setLayoutManager(linearLayoutManager);
        medHomeAdapter = new MedHomeAdapter(getContext(),this);

        medHomeAdapter.setMedInfo(medicines);

        allMed_rv.setAdapter(medHomeAdapter);

    }

    @Override
    public void onCLick(Medicine medicine) {
        Log.d("TAG", "onCLick: " + medicine.getName());
    }
}