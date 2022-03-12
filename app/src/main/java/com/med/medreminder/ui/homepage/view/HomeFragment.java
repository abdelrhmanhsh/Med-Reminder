package com.med.medreminder.ui.homepage.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.med.medreminder.R;
import com.med.medreminder.databinding.FragmentHomeBinding;
import com.med.medreminder.db.ConcreteLocalSource;
import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.Repository;
import com.med.medreminder.ui.addmedicine.view.AddMedActivity;
import com.med.medreminder.ui.homepage.presenter.HomeMedPresenter;
import com.med.medreminder.ui.homepage.presenter.homeMedPresenterInterface;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class HomeFragment extends Fragment implements onMedClickListener, homeMedViewInterface {

    private FragmentHomeBinding binding;
    LinearLayoutManager linearLayoutManager;
    MedHomeAdapter medHomeAdapter;
    RecyclerView allMed_rv;
    FloatingActionButton addMed_floatBtn;
    //ArrayList<Medicine> medicines;

    homeMedPresenterInterface homeMedPresenterInterface;

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

       // medicines = new ArrayList<>();

//        medicines.add(new Medicine(R.drawable.pill_ic,"Vitamic C","Pill(s)","250","10:00","AM","gm","1"));
//        medicines.add(new Medicine(R.drawable.pill_ic,"Vitamic C","Pill(s)","250","10:00","AM","gm","1"));
//        medicines.add(new Medicine(R.drawable.pill_ic,"Vitamic C","Pill(s)","250","10:00","AM","gm","1"));
//        medicines.add(new Medicine(R.drawable.pill_ic,"Vitamic C","Pill(s)","250","10:00","AM","gm","1"));
//        medicines.add(new Medicine(R.drawable.pill_ic,"Vitamic C","Pill(s)","250","10:00","AM","gm","1"));

        allMed_rv.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        allMed_rv.setLayoutManager(linearLayoutManager);
        medHomeAdapter = new MedHomeAdapter(getContext(), this);
        allMed_rv.setAdapter(medHomeAdapter);

        homeMedPresenterInterface = new HomeMedPresenter(this, Repository.getInstance(getContext(),
                ConcreteLocalSource.getInstance(getContext())));

        Log.d("TAG", "HomeFragment: " + getViewLifecycleOwner());

        homeMedPresenterInterface.showAllStoredMedicines(getViewLifecycleOwner());
        Log.d("TAG", "onViewCreated: " + 1);

        addMed_floatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddMedActivity.class));
            }
        });
        Log.d("TAG", "onViewCreated: " + 2);


    }

    @Override
    public void onCLick(Medicine medicine) {
        Log.d("TAG", "onCLick: " + medicine.getName());
    }

    @Override
    public void getAllStoredMedicines(List<Medicine> medicines) {
        medHomeAdapter.setMedInfo(medicines);
    }
}