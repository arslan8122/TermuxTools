package com.linux.termuxtools;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;

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

public class MainActivity extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    private ProgressBar spinner;
    private ScrollView sv;
    private String str="open";
    public int count =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
//         str = intent.getStringExtra("message_key");
//         System.out.println("wowwCreate"+ str);
        spinner=(ProgressBar)findViewById(R.id.progressBar1);
        sv=(ScrollView)findViewById(R.id.hs);
        sv.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);

    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            sv.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.GONE);
        }
    },1000);




//        AdLoader adLoader = new AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110") // test
        //        AdLoader adLoader = new AdLoader.Builder(this, "ca-app-pub-2727672331058815/1574748037") // production

    System.out.println("wowwInside");
    AdLoader adLoader = new AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110") //test
//    AdLoader adLoader = new AdLoader.Builder(this, "ca-app-pub-2727672331058815/1574748037") //prod

            .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                @Override
                public void onNativeAdLoaded(NativeAd nativeAd) {


                    NativeTemplateStyle styles = new
                            NativeTemplateStyle.Builder().build();
                    TemplateView template = findViewById(R.id.my_template);
                    template.setVisibility(View.VISIBLE);
                    template.setStyles(styles);
                    template.setNativeAd(nativeAd);
                }
            })
            .build();

    adLoader.loadAd(new AdRequest.Builder().build());




    }



    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)

                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
    public void sendMessageLinux(View view) {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, //test
//                InterstitialAd.load(this,"ca-app-pub-2727672331058815/5825501152", adRequest, //production
//        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
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

        if (mInterstitialAd != null) {
            sv.setVisibility(View.GONE);
            spinner.setVisibility(View.VISIBLE);
            mInterstitialAd.show(MainActivity.this);
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    sv.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.GONE);
                    super.onAdDismissedFullScreenContent();
                    Intent intent = new Intent(MainActivity.this, LinuxGrid.class);
                        count++;
                    intent.putExtra("count",count);
                    startActivityForResult(intent,1);
                }
            });
        } else {
            count++;
            Intent intent = new Intent(MainActivity.this, LinuxGrid.class);
            intent.putExtra("count",count);

            startActivityForResult(intent,1);
        }





    }
    public void sendMessageTermux(View view) {
        AdRequest adRequest = new AdRequest.Builder().build();

        //ca-app-pub-3940256099942544/1033173712 test
        //ca-app-pub-2727672331058815/5825501152 production
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
        if (mInterstitialAd != null) {
            sv.setVisibility(View.GONE);
            spinner.setVisibility(View.VISIBLE);
            mInterstitialAd.show(MainActivity.this);
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    sv.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.GONE);
                    super.onAdDismissedFullScreenContent();

                    Intent intent = new Intent(MainActivity.this, Termux.class);
                    startActivityForResult(intent,1);
                }
            });
        } else {

            Intent intent = new Intent(MainActivity.this, Termux.class);
            startActivityForResult(intent,1);
        }
    }

}