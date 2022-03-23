package com.med.medreminder.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.med.medreminder.R;
import com.med.medreminder.ui.homepage.view.HomeActivity;
import com.med.medreminder.utils.Constants;
import com.med.medreminder.utils.YourPreference;

public class SplashActivity extends AppCompatActivity {

    ImageView logo_img;
    YourPreference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo_img = findViewById(R.id.logo_img);

        preference = YourPreference.getInstance(this);

        Animation fadein = AnimationUtils.loadAnimation(this, R.anim.fade_in_forsplash);
        logo_img.startAnimation(fadein);
        fadein.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (preference.getData(Constants.IS_LOGIN).equals("true")){
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
}