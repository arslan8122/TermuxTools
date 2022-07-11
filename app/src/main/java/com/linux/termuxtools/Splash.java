package com.linux.termuxtools;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class Splash extends AppCompatActivity {
Animation topAnim,bottomAnim;
ImageView image;
TextView text;
    private static final long COUNTER_TIME = 5;

    private long secondsRemaining;
   public boolean stop = false;
    Handler handler;

private static  int SPLASH_SCREEN=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    stop=true;
        setContentView(R.layout.activity_splash);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        topAnim= AnimationUtils.loadAnimation(this
                ,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this
                ,R.anim.bottom_animation);

        image=findViewById(R.id.imageView);
        text=findViewById(R.id.text);

        image.setAnimation(topAnim);
        text.setAnimation(bottomAnim);
        createTimer(COUNTER_TIME);
//        System.out.println("asdd");
//     handler=     new Handler();
//   handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                Intent intent=new Intent(Splash.this,MainActivity.class);
//                intent.putExtra("message_key", "open");
//                startActivity(intent);
//                finish();
//            }
//        },SPLASH_SCREEN);

}
    private void createTimer(long seconds) {


        CountDownTimer countDownTimer =
                new CountDownTimer(seconds * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        secondsRemaining = ((millisUntilFinished / 1000) + 1);

                    }

                    @Override
                    public void onFinish() {
                        secondsRemaining = 0;


                        Application application = getApplication();

                        // If the application is not an instance of MyApplication, log an error message and
                        // start the MainActivity without showing the app open ad.
                        if (!(application instanceof MyApplication)) {
//                            Log.e(LOG_TAG, "Failed to cast application to MyApplication.");
                            startMainActivity();
                            return;
                        }

                        // Show the app open ad.
                        ((MyApplication) application)
                                .showAdIfAvailable(
                                        Splash.this,
                                        new MyApplication.OnShowAdCompleteListener() {
                                            @Override
                                            public void onShowAdComplete() {
                                                startMainActivity();
                                            }
                                        });
                    }
                };
        countDownTimer.start();
    }

    /** Start the MainActivity. */
    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        finish();
    }
}