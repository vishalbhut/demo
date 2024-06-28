package com.vishu.ads;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;

public class AdsPreloadUtils {
    public static InterstitialAd interstitialAdmob;

    final Activity context;
    public boolean isRunning = true;
    public AdLoader smallNativeAdLoader;
    public NativeAd smallNativeAdmob;

    public AdsPreloadUtils(Activity activity) {
        this.context = activity;
    }





    public void callPreloadInterstitialDouble(String str, final String str2) {
        if (!isNetworkAvailable(context)) {
            return;
        }
        if (!str.equalsIgnoreCase("11")) {
            if (this.isRunning) {
                this.isRunning = false;
                if (interstitialAdmob == null) {
                    InterstitialAd.load(this.context, str, new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
                        public void onAdLoaded(InterstitialAd interstitialAd) {
                            AdsPreloadUtils.interstitialAdmob = interstitialAd;
                            isRunning = true;
                        }

                        public void onAdFailedToLoad(LoadAdError loadAdError) {
                            AdsPreloadUtils.interstitialAdmob = null;
                            InterstitialAd.load(context, str2, new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
                                public void onAdLoaded(InterstitialAd interstitialAd) {
                                    AdsPreloadUtils.interstitialAdmob = interstitialAd;
                                    isRunning = true;
                                }

                                public void onAdFailedToLoad(LoadAdError loadAdError) {

                                    AdsPreloadUtils.interstitialAdmob = null;
                                    isRunning = true;
                                }
                            });
                        }
                    });
                } else {
                    this.isRunning = true;
                }
            }
        } else if (str2.equalsIgnoreCase("11")) {
            interstitialAdmob = null;
        } else if (this.isRunning) {
            this.isRunning = false;
            if (interstitialAdmob == null) {
                InterstitialAd.load(this.context, str2, new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
                    public void onAdLoaded(InterstitialAd interstitialAd) {
                        AdsPreloadUtils.interstitialAdmob = interstitialAd;
                        isRunning = true;
                    }

                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        AdsPreloadUtils.interstitialAdmob = null;
                        InterstitialAd.load(context, str2, new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
                            public void onAdLoaded(InterstitialAd interstitialAd) {
                                AdsPreloadUtils.interstitialAdmob = interstitialAd;
                                isRunning = true;
                            }

                            public void onAdFailedToLoad(LoadAdError loadAdError) {

                                AdsPreloadUtils.interstitialAdmob = null;
                                isRunning = true;
                            }
                        });
                    }
                });
            } else {
                this.isRunning = true;
            }
        }
    }



    public void callPreloadSmallNativeDouble(String str, final String str2) {
        if (!str.equalsIgnoreCase("11")) {
            if (this.smallNativeAdmob == null) {
                final NativeAdOptions build = new NativeAdOptions.Builder().setVideoOptions(new VideoOptions.Builder().setStartMuted(false).build()).build();
                AdLoader build2 = new AdLoader.Builder(this.context, str).withAdListener(new AdListener() {
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        smallNativeAdLoader = new AdLoader.Builder(context, str2).withAdListener(new AdListener() {
                            public void onAdFailedToLoad(LoadAdError loadAdError) {
                                super.onAdFailedToLoad(loadAdError);
                            }
                        }).withNativeAdOptions(build).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                            public void onNativeAdLoaded(NativeAd nativeAd) {
                                if (!smallNativeAdLoader.isLoading()) {
                                    smallNativeAdmob = nativeAd;
                                }
                            }
                        }).build();
                        smallNativeAdLoader.loadAd(new AdRequest.Builder().build());
                    }
                }).withNativeAdOptions(build).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        if (!smallNativeAdLoader.isLoading()) {
                            smallNativeAdmob = nativeAd;
                        }
                    }
                }).build();
                this.smallNativeAdLoader = build2;
                build2.loadAd(new AdRequest.Builder().build());
            }
        } else if (!str2.equalsIgnoreCase("11") && this.smallNativeAdmob == null) {
            AdLoader build3 = new AdLoader.Builder(this.context, str2).withAdListener(new AdListener() {
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                }
            }).withNativeAdOptions(new NativeAdOptions.Builder().setVideoOptions(new VideoOptions.Builder().setStartMuted(false).build()).build()).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
 
                public void onNativeAdLoaded(NativeAd nativeAd) {
                    if (!smallNativeAdLoader.isLoading()) {
                        smallNativeAdmob = nativeAd;
                    }
                }
            }).build();
            this.smallNativeAdLoader = build3;
            build3.loadAd(new AdRequest.Builder().build());
        }
    }


    public static boolean isNetworkAvailable(Context bgChangeActivity) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) bgChangeActivity.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
