package com.med.medreminder.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

import com.med.medreminder.db.ConcreteLocalSource;
import com.med.medreminder.firebase.FirebaseHelper;
import com.med.medreminder.firebase.FirebaseWork;
import com.med.medreminder.model.Repository;
import com.med.medreminder.model.RepositoryInterface;
import com.med.medreminder.utils.Constants;

public class DailyTakeReceiver extends BroadcastReceiver {

    RepositoryInterface repo;

    @Override
    public void onReceive(Context context, Intent intent) {
        long id = intent.getLongExtra(Constants.MED_ID, -1);
        int amountLeft = intent.getIntExtra(Constants.AMOUNT_LEFT, -1);

        repo = Repository.getInstance(context,
                ConcreteLocalSource.getInstance(context), FirebaseWork.getInstance(context));

        String userEmail = FirebaseHelper.getUserEmail(context);

        int newAmount = amountLeft-1;
        updateMedAmount(id, newAmount);
        updateMedAmountFirestore(userEmail, id, newAmount);
        Toast.makeText(context, "Medicine Taken!", Toast.LENGTH_SHORT).show();
        NotificationManagerCompat.from(context).cancel(100);
    }

    private void updateMedAmount(long id, int newAmount) {
        repo.updateMedAmount(id, newAmount);
    }

    private void updateMedAmountFirestore(String email, long id, int newAmount) {
        repo.updateMedAmountFirestore(email, id, newAmount);
    }

}