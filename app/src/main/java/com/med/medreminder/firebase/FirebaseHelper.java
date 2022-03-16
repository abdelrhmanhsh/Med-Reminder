package com.med.medreminder.firebase;

import android.content.Context;

import com.med.medreminder.utils.Constants;
import com.med.medreminder.utils.YourPreference;

public class FirebaseHelper {

    static YourPreference yourPreference;

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
