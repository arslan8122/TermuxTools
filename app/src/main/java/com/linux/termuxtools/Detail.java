package com.linux.termuxtools;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;

public class Detail extends AppCompatActivity {
    ImageButton b1;

    private ClipboardManager myClipboard;
    private ClipData myClip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
//ca-app-pub-2727672331058815/1574748037 //production
//        ca-app-pub-3940256099942544/2247696110 //test
        AdLoader adLoader = new AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110") //test
//        AdLoader adLoader = new AdLoader.Builder(this, "ca-app-pub-2727672331058815/1574748037") //production

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

        b1 = (ImageButton) findViewById(R.id.imageButton);
        TextView receiver_msg = (TextView)findViewById(R.id.textView);
        Intent intent = getIntent();
        String str = intent.getStringExtra("message_key");
receiver_msg.setMovementMethod(new ScrollingMovementMethod());
        // display the string into textView
        receiver_msg.setText(str);



        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                myClip = ClipData.newPlainText("text", str);
                myClipboard.setPrimaryClip(myClip);

                Toast.makeText(getApplicationContext(), "Text Copied",
                        Toast.LENGTH_SHORT).show();
            }
        });
//        int height_in_pixels = receiver_msg.getLineCount() * receiver_msg.getLineHeight(); //approx height text
//        receiver_msg.setHeight(height_in_pixels);

    }

}