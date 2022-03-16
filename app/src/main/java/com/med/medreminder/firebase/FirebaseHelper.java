package com.med.medreminder.firebase;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.med.medreminder.utils.Constants;
import com.med.medreminder.utils.YourPreference;

public class FirebaseHelper {

    static YourPreference yourPreference;

    public static boolean isInternetAvailable(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        return nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
    }

    public static boolean isUserLoggedIn(Context context){
        boolean isLogin = false;
        yourPreference = YourPreference.getInstance(context);

        String isLoginStr = yourPreference.getData(Constants.IS_LOGIN);
        if(isLoginStr.equals("true"))
            isLogin = true;

        return isLogin;
    }

    public static String getUserEmail(Context context){
        yourPreference = YourPreference.getInstance(context);
        return yourPreference.getData(Constants.EMAIL);
    }

}
