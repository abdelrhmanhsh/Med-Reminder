package com.med.medreminder.ui.addmedicine.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.med.medreminder.R;

import org.json.JSONException;
import org.json.JSONObject;

public class AddMedChangeIconFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddMedChangeIconFragment";

    TextView textTitle;
    ProgressBar progressBar;
    ImageView imgPill, imgInjection, imgDrops, imgOther;
    String incomingMedicine;
    JSONObject outgoingMedicine;

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

        outgoingMedicine = getArgs();

    }

    private JSONObject getArgs(){
        AddMedChangeIconFragmentArgs args = AddMedChangeIconFragmentArgs.fromBundle(getArguments());
        incomingMedicine = args.getMedicine();

        Log.i(TAG, "getArgs: " + incomingMedicine);

        JSONObject incomingJson = null;

        try {
            incomingJson = new JSONObject(incomingMedicine);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String title = "Unknown";
        try {
            title = incomingJson.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        textTitle.setText(title);
        return incomingJson;
    }

    private void actionIconSelected(View view, int imgResource){
        try {
            outgoingMedicine.put("image", imgResource);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String medicine = outgoingMedicine.toString();

        AddMedChangeIconFragmentDirections.ActionAddMedChangeIconToAlmost
                action = AddMedChangeIconFragmentDirections.actionAddMedChangeIconToAlmost();
        action.setAlmost(medicine);
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