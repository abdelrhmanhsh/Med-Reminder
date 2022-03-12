package com.med.medreminder.ui.addmedicine.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.med.medreminder.R;
import com.med.medreminder.model.Medicine;

public class AddMedChangeIconFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddMedChangeIconFragment";

    TextView textTitle;
    ProgressBar progressBar;
    ImageView imgPill, imgInjection, imgDrops, imgOther;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_med_change_icon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textTitle = view.findViewById(R.id.title);
        progressBar = view.findViewById(R.id.progress_bar);
        imgPill = view.findViewById(R.id.med_pill_icon);
        imgInjection = view.findViewById(R.id.med_injection_icon);
        imgDrops = view.findViewById(R.id.med_drops_icon);
        imgOther = view.findViewById(R.id.med_other_icon);

        progressBar.setProgress(90);

        imgPill.setOnClickListener(this);
        imgInjection.setOnClickListener(this);
        imgDrops.setOnClickListener(this);
        imgOther.setOnClickListener(this);

        setTitleText();

    }

    private void setTitleText(){
        Medicine medicine = Medicine.getInstance();
        textTitle.setText(medicine.getName());
    }

    private void actionIconSelected(View view, int imgResource){
        Medicine medicine = Medicine.getInstance();
        medicine.setImage(imgResource);

        NavDirections action = AddMedChangeIconFragmentDirections.actionAddMedChangeIconToAlmost();
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.med_pill_icon:
                actionIconSelected(view, R.drawable.ic_pill);
                break;

            case R.id.med_injection_icon:
                actionIconSelected(view, R.drawable.ic_injection);
                break;

            case R.id.med_drops_icon:
                actionIconSelected(view, R.drawable.ic_drops);
                break;

            case R.id.med_other_icon:
                actionIconSelected(view, R.drawable.ic_medicine_other);
                break;

            default:
                actionIconSelected(view, R.drawable.ic_medicine_other);
                break;
        }
    }
}