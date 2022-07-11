package com.linux.termuxtools;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Linux extends AppCompatActivity {
    ListView lv;
    ArrayList<HashMap<String,String>> arrayList;
    private InterstitialAd mInterstitialAd;
    public int strData;
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linux);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        //ca-app-pub-2727672331058815/1574748037 //production
        //ca-app-pub-3940256099942544/2247696110  //test
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


        Intent intent = getIntent();
        strData = intent.getIntExtra("count",0);

        str = intent.getStringExtra("message_value");
        arrayList=new ArrayList<>();
        lv = (ListView)findViewById(R.id.listview);
        try {
            JSONArray superheros  = new JSONArray(readJSONFromAsset());
            System.out.println("json size is : "+superheros);
            for(int i = 0;i<superheros.length();i++)
            {
                JSONObject hero = superheros.getJSONObject(i);
                String name = hero.getString("name");
                String power = hero.getString("detail");
                System.out.println(i+" Name: "+name +" Power : "+power);
                HashMap<String,String> data = new HashMap<>();
                data.put("name",name);
                data.put("detail",power);
//                System.out.println(data+" Name: ");

                arrayList.add(data);
                ListAdapter adapter = new SimpleAdapter(Linux.this,arrayList,R.layout.listview_layout
                        ,new String[]{"name"},new int[]{R.id.name});
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView songName = view.findViewById(R.id.name);
                        Intent intent = new Intent(Linux.this, Detail.class);
                        System.out.println("notout"+arrayList.get(i).get("name"));



                        AdRequest adRequest = new AdRequest.Builder().build();
//ca-app-pub-2727672331058815/5825501152 //production
                        //ca-app-pub-3940256099942544/1033173712 //test
                        InterstitialAd.load(view.getContext(),"ca-app-pub-3940256099942544/1033173712", adRequest, //test
//                                InterstitialAd.load(view.getContext(),"ca-app-pub-2727672331058815/5825501152", adRequest, //production
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
                        System.out.println("linux"+(strData%3));

                        if(strData % 3 == 0) {
                            if (mInterstitialAd != null) {
                                mInterstitialAd.show(Linux.this);
                                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        super.onAdDismissedFullScreenContent();
                                        intent.putExtra("message_key", arrayList.get(i).get("detail").toString());

                                        startActivity(intent);
                                    }
                                });
                            } else {

                                intent.putExtra("message_key", arrayList.get(i).get("detail").toString());

                                startActivity(intent);
                            }
                        }
                        else {

                            intent.putExtra("message_key", arrayList.get(i).get("detail").toString());

                            startActivity(intent);
                        }
                    }
                });

            }
        } catch ( JSONException e) {
            System.out.println("json error "+e);


            e.printStackTrace();
        }
    }
    public String readJSONFromAsset() {
        String json = null;

        try {
            InputStream is=null;
            if (str.equals("3")){
                is = getAssets().open("basic.json");
            }
            if (str.equals("4")){
                is = getAssets().open("advance.json");
            }

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }


        return json;
    }
}