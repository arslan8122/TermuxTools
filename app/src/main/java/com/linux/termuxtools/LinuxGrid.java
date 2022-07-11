package com.linux.termuxtools;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;

public class LinuxGrid extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    public int strData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linux_grid);
        Intent intent = getIntent();

         strData = intent.getIntExtra("count",0);

        System.out.println("data:"+strData);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        //ca-app-pub-3940256099942544/2247696110 test
        //ca-app-pub-2727672331058815/1574748037 prod
        AdLoader adLoader = new AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110") //test
//        AdLoader adLoader = new AdLoader.Builder(this, "ca-app-pub-2727672331058815/1574748037") //prod

                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().build();
                        TemplateView template = findViewById(R.id.my_template);
                        template.setStyles(styles);
                        template.setNativeAd(nativeAd);
                    }
                })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    public void sendMessageLinux3(View view) {
        AdRequest adRequest = new AdRequest.Builder().build();
//ca-app-pub-2727672331058815/5825501152  //production
        //ca-app-pub-3940256099942544/1033173712  //test
        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, //test
//                InterstitialAd.load(this,"ca-app-pub-2727672331058815/5825501152", adRequest, //prod

                        new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i("TAG", "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("TAGSS", loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });
        System.out.println("good"+(strData%3));
        strData++;
        if(strData % 3 == 0) {
            if (mInterstitialAd != null) {
                mInterstitialAd.show(LinuxGrid.this);
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();


                        Intent intent = new Intent(LinuxGrid.this, Linux.class);
                        intent.putExtra("message_value", "3");


                        startActivity(intent);
                    }
                });
            } else {


                Intent intent = new Intent(LinuxGrid.this, Linux.class);
                intent.putExtra("message_value", "3");

                startActivity(intent);
            }

        }
        else {


            Intent intent = new Intent(LinuxGrid.this, Linux.class);
            intent.putExtra("message_value", "3");

            startActivity(intent);
        }


    }
    public void sendMessageLinux4(View view) {





        AdRequest adRequest = new AdRequest.Builder().build();
//ca-app-pub-2727672331058815/5825501152  /production
        //ca-app-pub-3940256099942544/1033173712 /test/
        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, //test
//                InterstitialAd.load(this,"ca-app-pub-2727672331058815/5825501152", adRequest, //prod

                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i("TAG", "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("TAGSS", loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });
        strData++;
        System.out.println("good" + (strData % 3));
        if(strData % 3 == 0) {
            if (mInterstitialAd != null) {
                mInterstitialAd.show(LinuxGrid.this);
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        Intent intent = new Intent(LinuxGrid.this, Linux.class);
                        intent.putExtra("message_value", "4");

                        startActivity(intent);
                    }
                });
            } else {

                Intent intent = new Intent(LinuxGrid.this, Linux.class);
                intent.putExtra("message_value", "4");

                startActivity(intent);
            }
        }
        else {

            Intent intent = new Intent(LinuxGrid.this, Linux.class);
            intent.putExtra("message_value", "4");

            startActivity(intent);
        }

    }
    @Override
    public void onBackPressed() {
        System.out.println("wowwwGrid");
        Intent intent=new Intent(LinuxGrid.this,MainActivity.class);
        intent.putExtra("message_key", "close");
        setResult(RESULT_OK, intent);

        finish();
    }
}