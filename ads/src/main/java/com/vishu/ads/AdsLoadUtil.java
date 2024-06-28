package com.vishu.ads;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;


public class AdsLoadUtil {
    final Context context;
    final ProgressDialog progressDialog;

    public interface FullscreenAds {
        void changeFlag();

        void loadToFail();

        void nextActivity();
    }

    public AdsLoadUtil(Context context2) {
        this.context = context2;
        ProgressDialog progressDialog2 = new ProgressDialog(context2, R.style.MyProgressDialog);
        this.progressDialog = progressDialog2;
        progressDialog2.setCancelable(false);
        this.progressDialog.setMessage(context2.getResources().getString(R.string.showingads));
    }

    public void callAdMobAds(String str, Activity activity, FullscreenAds fullscreenAds) {
        if (isNetworkAvailable()) {
            if (!str.equalsIgnoreCase("11")) {
                if (AdsVariable.ads_dialog.equalsIgnoreCase("1")) {
                    this.progressDialog.show();
                }
                new Handler().postDelayed(new AnonymousClass1(str, fullscreenAds, activity), 1000L);
                return;
            }
            try {
                ProgressDialog progressDialog = this.progressDialog;
                if (progressDialog != null && progressDialog.isShowing()) {
                    this.progressDialog.dismiss();
                }
            } catch (Exception ignored) {
            }
            fullscreenAds.loadToFail();
            return;
        }
        try {
            ProgressDialog progressDialog2 = this.progressDialog;
            if (progressDialog2 != null && progressDialog2.isShowing()) {
                this.progressDialog.dismiss();
            }
        } catch (Exception ignored) {
        }
        fullscreenAds.loadToFail();
    }

    public class AnonymousClass1 implements Runnable {
        final  Activity val$activity;
        final  String val$adMobId;
        final  FullscreenAds val$fullscreenAds;

        AnonymousClass1(String str, FullscreenAds fullscreenAds, Activity activity) {
            this.val$adMobId = str;
            this.val$fullscreenAds = fullscreenAds;
            this.val$activity = activity;
        }

        @Override
        public void run() {
            try {
                if (AdsPreloadUtils.interstitialAdmob != null && !this.val$adMobId.equalsIgnoreCase("11")) {
                    AdsVariable.needToShow = true;
                    AdsPreloadUtils.interstitialAdmob.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            AdsVariable.needToShow = false;
                            try {
                                if (progressDialog != null && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            } catch (Exception ignored) {
                            }
                            val$fullscreenAds.changeFlag();
                            val$fullscreenAds.nextActivity();
                            new AdsPreloadUtils(val$activity).callPreloadInterstitialDouble(AdsVariable.fullscreen_preload_high, AdsVariable.fullscreen_preload_medium);
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            AdsVariable.needToShow = false;
                            try {
                                if (progressDialog != null && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            } catch (Exception ignored) {
                            }
                            val$fullscreenAds.loadToFail();
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            AdsPreloadUtils.interstitialAdmob = null;
                            try {
                                if (progressDialog == null || !progressDialog.isShowing()) {
                                    return;
                                }
                                progressDialog.dismiss();
                            } catch (Exception ignored) {
                            }
                        }
                    });
                    AdsPreloadUtils.interstitialAdmob.show(this.val$activity);
                } else if (!this.val$adMobId.equalsIgnoreCase("11") && AdsVariable.ads_load_all.equalsIgnoreCase("1")) {
                    try {
                        if (progressDialog != null && !progressDialog.isShowing()) {
                            progressDialog.show();
                        }
                    } catch (Exception ignored) {
                    }
                    InterstitialAd.load(this.val$activity, this.val$adMobId, new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(InterstitialAd interstitialAd) {
                            interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdShowedFullScreenContent() {
                                }

                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    AdsVariable.needToShow = false;
                                    try {
                                        if (progressDialog != null && progressDialog.isShowing()) {
                                            progressDialog.dismiss();
                                        }
                                    } catch (Exception ignored) {
                                    }
                                    val$fullscreenAds.changeFlag();
                                    val$fullscreenAds.nextActivity();
                                    new AdsPreloadUtils(val$activity).callPreloadInterstitialDouble(AdsVariable.fullscreen_preload_high, AdsVariable.fullscreen_preload_medium);
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    AdsVariable.needToShow = false;
                                    try {
                                        if (progressDialog != null && progressDialog.isShowing()) {
                                            progressDialog.dismiss();
                                        }
                                    } catch (Exception ignored) {
                                    }
                                    val$fullscreenAds.loadToFail();
                                }
                            });
                            AdsVariable.needToShow = true;
                            interstitialAd.show(val$activity);
                            try {
                                if (progressDialog == null || !progressDialog.isShowing()) {
                                    return;
                                }
                                progressDialog.dismiss();
                            } catch (Exception ignored) {
                            }
                        }

                        @Override
                        public void onAdFailedToLoad(LoadAdError loadAdError) {
                            try {
                                if (progressDialog != null && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            } catch (Exception ignored) {
                            }
                            AdsVariable.needToShow = false;
                            val$fullscreenAds.loadToFail();
                        }
                    });
                } else {
                    try {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    } catch (Exception ignored) {
                    }
                    this.val$fullscreenAds.loadToFail();
                }
            } catch (Exception e) {
                try {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                } catch (Exception ignored) {
                }
                this.val$fullscreenAds.loadToFail();
            }
        }
    }

    public void callAdMobAdsSplash(String str, final Activity activity, final FullscreenAds fullscreenAds) {
        if (!isNetworkAvailable()) {
            fullscreenAds.loadToFail();
        } else if (!str.equalsIgnoreCase("11")) {
            try {
                if (!str.equalsIgnoreCase("11")) {
                    InterstitialAd.load(activity, str, new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
                        public void onAdLoaded(InterstitialAd interstitialAd) {
                            interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                public void onAdShowedFullScreenContent() {
                                }

                                public void onAdDismissedFullScreenContent() {
                                    AdsVariable.needToShow = false;
                                    fullscreenAds.changeFlag();
                                    fullscreenAds.nextActivity();
                                    new AdsPreloadUtils(activity).callPreloadInterstitialDouble(AdsVariable.fullscreen_preload_high, AdsVariable.fullscreen_preload_medium);
                                }

                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    AdsVariable.needToShow = false;
                                    fullscreenAds.loadToFail();
                                }
                            });
                            AdsVariable.needToShow = true;
                            interstitialAd.show(activity);
                        }

                        public void onAdFailedToLoad(LoadAdError loadAdError) {
                            AdsVariable.needToShow = false;
                            fullscreenAds.loadToFail();
                        }
                    });
                } else {
                    fullscreenAds.loadToFail();
                }
            } catch (Exception e) {
                fullscreenAds.loadToFail();
            }
        } else {
            fullscreenAds.loadToFail();
        }
    }

// 



    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
