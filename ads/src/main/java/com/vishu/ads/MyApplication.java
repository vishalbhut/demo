package com.vishu.ads;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks, LifecycleObserver {
    public static String APP_FOLDER = "FaceSwap";
    public static File mSdCard = new File("/storage/emulated/0/Android/data/" + "com.nixa.snapface/files");
    public static File APP_DIRECTORY = new File(mSdCard, APP_FOLDER);
    public static String CUSTOM_ACTION_APP_UPDATE = "com.faceapp.custom_action_app_update";



    public static String App_url="https://trandyapp.com/Ai/";
    public static String BASE_URL = "https://aicup.magicutapp.com/";
    public static String p_path="com.video.reface.app.faceplay.deepface.photo";

    public static AppOpenAdManager appOpenAdManager;
    public Activity currentActivity;
    public static int lastitemselector=-1;
    public interface OnShowAdCompleteListener {
        void onShowAdComplete();

    }
    String url;
    public AppOpenAd.AppOpenAdLoadCallback loadCallback;
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity activity) {
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityStopped(Activity activity) {
    }
    static MyApplication instance;
    public static MyApplication getInstance() {
        return instance;
    }
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        mSdCard = getExternalFilesDir(null);

        FirebaseApp.initializeApp(this);
        if (isNetworkConnected(instance)){
            FirebaseApp.initializeApp(this);
            mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
            mFirebaseRemoteConfig.fetchAndActivate()
                    .addOnCompleteListener(new OnCompleteListener<Boolean>() {
                        @Override
                        public void onComplete(@NonNull Task<Boolean> task) {

                            if (task.isSuccessful()) {
                                boolean updated = task.getResult();
                                Log.d("RemoteValue", "Config params updated: " + updated);
                                // setRemoteValueToPref();
                                if (updated) {
                                    updateFirebaseFetchValue();
                                }
                            }
                        }
                    });
            registerActivityLifecycleCallbacks(this);
            ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
            appOpenAdManager = new AppOpenAdManager();


        }


        try {
            File file = new File(getCacheDir() + "/tempImages");
            if (!file.exists()) {
                file.mkdirs();
            }

            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e10) {
            e10.printStackTrace();
        }
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onMoveToForeground() {
        if (!AdsVariable.needToShow) {
            appOpenAdManager.showAdIfAvailable(this.currentActivity);
        }
    }

    public void onActivityStarted(Activity activity) {
        if (!appOpenAdManager.isShowingAd) {
            this.currentActivity = activity;
        }
    }

    public void showAdIfAvailable(Activity activity, OnShowAdCompleteListener onShowAdCompleteListener) {
        appOpenAdManager.showAdIfAvailable(activity, onShowAdCompleteListener);
    }
    public void urlsdata(String spath) {
        try {
            JSONObject jsonObject = new JSONObject(spath);
            JSONObject v4 = jsonObject.getJSONObject("v4");
            String base_url = v4.getString("version");
            String baseUrl = v4.getString("baseUrl");
            String upload_image = v4.getString("upload_image");
            BASE_URL = base_url;
            p_path = baseUrl;

            App_url = upload_image;


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public class AppOpenAdManager {


        public AppOpenAd appOpenAd = null;
        public boolean isFaildLoadingAd = false;

        public boolean isLoadingAd = false;

        public boolean isShowingAd = false;

        public long loadTime = 0;

        public AppOpenAdManager() {
        }

        public void loadAd(Context context) {
            if (!this.isLoadingAd && !isAdAvailable()) {
                this.isFaildLoadingAd = false;
                this.isLoadingAd = true;
                 loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
                    public void onAdLoaded(AppOpenAd appOpenAd1) {
                        super.onAdLoaded(AppOpenAdManager.this.appOpenAd);
                        appOpenAd = appOpenAd1;
                         isLoadingAd = false;
                         loadTime = new Date().getTime();
                    }

                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                          isLoadingAd = false;
                         isFaildLoadingAd = true;
                    }
                };
                AppOpenAd.load(context, AdsVariable.appopen, new AdRequest.Builder().build(), loadCallback);
            }
        }

        private boolean wasLoadTimeLessThanNHoursAgo() {
            return new Date().getTime() - this.loadTime < (long) 4 * 3600000;
        }

        public boolean isAdAvailable() {
            return this.appOpenAd != null && wasLoadTimeLessThanNHoursAgo();
        }

        public boolean isFaildLoadingAd() {
            return this.isFaildLoadingAd;
        }


        public void showAdIfAvailable(Activity activity) {
            showAdIfAvailable(activity, new OnShowAdCompleteListener() {
                public void onShowAdComplete() {
                }
            });
        }


        public void showAdIfAvailable(final Activity activity, final OnShowAdCompleteListener onShowAdCompleteListener) {
            if (this.isShowingAd) {
            } else if (!isAdAvailable()) {
                onShowAdCompleteListener.onShowAdComplete();
                loadAd(activity);
            } else {
                this.appOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    public void onAdDismissedFullScreenContent() {
                        AppOpenAdManager.this.appOpenAd = null;
                        AppOpenAdManager.this.isShowingAd = false;
                        onShowAdCompleteListener.onShowAdComplete();
                        AppOpenAdManager.this.loadAd(activity);
                    }

                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        AppOpenAdManager.this.appOpenAd = null;
                        AppOpenAdManager.this.isShowingAd = false;
                        onShowAdCompleteListener.onShowAdComplete();
                        AppOpenAdManager.this.loadAd(activity);
                    }

                    public void onAdShowedFullScreenContent() {
                        AppOpenAdManager.this.isShowingAd = true;
                    }
                });
                this.appOpenAd.show(MyApplication.this.currentActivity);
            }
        }
    }
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;

    private void updateFirebaseFetchValue() {

        SharedPreferences sharedPreferences2 = getSharedPreferences(getString(R.string.app_name), 0);
        sharedPreferences = sharedPreferences2;
        this.myEdit = sharedPreferences2.edit();

        myEdit.commit();

    }
    public   boolean isNetworkConnected(Context lCon) {
        ConnectivityManager cm = null;
        try {
            cm = (ConnectivityManager) lCon
                    .getSystemService(Context.CONNECTIVITY_SERVICE);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return cm.getActiveNetworkInfo() != null;
    }
}
