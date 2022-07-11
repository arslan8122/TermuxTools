package com.linux.termuxtools;

import static androidx.lifecycle.Lifecycle.Event.ON_START;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.util.Date;

public class AppOpenManager implements LifecycleObserver,Application.ActivityLifecycleCallbacks {
    private long loadTime = 0;
    private Activity currentActivity;
    private MainActivity spinner;
    private MainActivity sv;
    private static final String LOG_TAG = "AppOpenManager";
//    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/3419835294"; //test


    private static final String AD_UNIT_ID = "ca-app-pub-2727672331058815/9573174478"; //production
//    private static final String AD_UNIT_ID = "ca-app-pub-2727672331058815/9573174478";
    private AppOpenAd appOpenAd = null;
    private static boolean isShowingAd = false;
    private AppOpenAd.AppOpenAdLoadCallback loadCallback;

    private final MyApplication myApplication;

    /** Constructor */
    public AppOpenManager(MyApplication myApplication) {
        this.myApplication = myApplication;
        this.myApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
//        spinner=(ProgressBar) currentActivity.findViewById(R.id.progressBar1);
//        sv=(ScrollView)currentActivity.findViewById(R.id.hs);
    }
    public void showAdIfAvailable() {
        // Only show ad if there is not already an app open ad currently showing
        // and an ad is available.
        System.out.println("asdd1");
        if (!isShowingAd && isAdAvailable()) {
            System.out.println("asdd2");
            Log.d(LOG_TAG, "Will show ad.");

            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {


                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Set the reference to null so isAdAvailable() returns false.

                            System.out.println("asdd3");
                            AppOpenManager.this.appOpenAd = null;
                            isShowingAd = false;
                            Intent i=new Intent(currentActivity,MainActivity.class);
                            currentActivity.startActivity(i);
                            fetchAd();

                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            System.out.println("asdd31");
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {

                            System.out.println("asdd41");
                            isShowingAd = true;
                        }

                    };


            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAd.show(currentActivity);
            System.out.println("asdd12");

        } else {
            System.out.println("asdd4");
            Log.d(LOG_TAG, "Can not show ad.");
            fetchAd();
        }
    }

    /** Request an ad */
    public void fetchAd() {
        // Have unused ad, no need to fetch another.
        System.out.println("asdd24");

        if (isAdAvailable()) {
            System.out.println("asdd25");

            return;
        }

        loadCallback =
                new AppOpenAd.AppOpenAdLoadCallback() {
                    /**
                     * Called when an app open ad has loaded.
                     *
                     * @param ad the loaded app open ad.
                     */
                    @Override
                    public void onAdLoaded(AppOpenAd ad) {
                        System.out.println("asdd27");

                        AppOpenManager.this.appOpenAd = ad;
                        appOpenAd.show(currentActivity);
                        AppOpenManager.this.loadTime = (new Date()).getTime();


                    }

                    /**
                     * Called when an app open ad has failed to load.
                     *
                     * @param loadAdError the error.
                     */
                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        // Handle the error.
                        System.out.println("asdd28");

                    }

                };
        AdRequest request = getAdRequest();
        AppOpenAd.load(
                myApplication, AD_UNIT_ID, request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
    }
    /** Utility method to check if ad was loaded more than n hours ago. */
    private boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
        long dateDifference = (new Date()).getTime() - this.loadTime;
        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour * numHours));
    }

    /** Creates and returns ad request. */
    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    /** Utility method that checks if ad exists and can be shown. */
    public boolean isAdAvailable() {

        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}

    @Override
    public void onActivityStarted(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStopped(Activity activity) {}

    @Override
    public void onActivityPaused(Activity activity) {}

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {}

    @Override
    public void onActivityDestroyed(Activity activity) {
        currentActivity = null;
    }
    @OnLifecycleEvent(ON_START)
    public void onStart() {
new Handler().postDelayed(new Runnable() {
    @Override
    public void run() {
        showAdIfAvailable();
    }
},3000);




        Log.d(LOG_TAG, "onStart");
    }

}
