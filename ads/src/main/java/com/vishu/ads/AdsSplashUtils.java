package com.vishu.ads;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.os.Handler;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.ump.ConsentForm;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.FormError;
import com.google.android.ump.UserMessagingPlatform;


import java.util.ArrayList;
import java.util.List;

public class AdsSplashUtils {

    final Activity activity;
    final AdsLoadUtil adsLoadUtil;

    
    public ConsentInformation consentInformation;

    SharedPreferences.Editor myEdit;
    
    public long secondsRemaining;

    SharedPreferences sharedPreferences;
    final SplashInterface splashInterface;

    public interface SplashInterface {
        void callNativePreload();

        void nextActivity();
    }

    public AdsSplashUtils(Activity activity2, SplashInterface splashInterface2) {
        this.activity = activity2;


        this.splashInterface = splashInterface2;
        this.adsLoadUtil = new AdsLoadUtil(activity2);

        getOnlineIds();
    }

    public void getOnlineIds() {
        Activity activity2 = this.activity;
        SharedPreferences sharedPreferences2 = activity2.getSharedPreferences(activity2.getString(R.string.app_name), 0);
        this.sharedPreferences = sharedPreferences2;
        this.myEdit = sharedPreferences2.edit();
        AdsVariable.ads_load_all = this.sharedPreferences.getString("ads_load_all", "1");
        AdsVariable.fullscreen_preload_high = this.sharedPreferences.getString("fullscreen_preload_high", "ca-app-pub-3940256099942544/1033173712");
        AdsVariable.fullscreen_preload_medium = this.sharedPreferences.getString("fullscreen_preload_medium", "ca-app-pub-3940256099942544/1033173712");
        AdsVariable.fullscreen_preload_normal = this.sharedPreferences.getString("fullscreen_preload_normal", "ca-app-pub-3940256099942544/1033173712");
        AdsVariable.appopenSplash = this.sharedPreferences.getString("appopenSplash", "ca-app-pub-3940256099942544/9257395921");
        AdsVariable.appopen = this.sharedPreferences.getString("appopen", "ca-app-pub-3940256099942544/9257395921");
        AdsVariable.ads_dialog = this.sharedPreferences.getString("ads_dialog", "1");
        AdsVariable.full_Splash_Activity_high = this.sharedPreferences.getString("full_Splash_Activity_high", "ca-app-pub-3940256099942544/1033173712");
        AdsVariable.full_Splash_Activity_normal = this.sharedPreferences.getString("full_Splash_Activity_normal", "ca-app-pub-3940256099942544/1033173712");
        AdsVariable.fullscreen_purchase = this.sharedPreferences.getString("fullscreen_purchase", "ca-app-pub-3940256099942544/1033173712");
        AdsVariable.native_language_high = this.sharedPreferences.getString("native_language_high", "ca-app-pub-3940256099942544/2247696110");
        AdsVariable.native_language = this.sharedPreferences.getString("native_language", "ca-app-pub-3940256099942544/2247696110");
        AdsVariable.native_intro_high = this.sharedPreferences.getString("native_intro_high", "ca-app-pub-3940256099942544/2247696110");
        AdsVariable.native_intro = this.sharedPreferences.getString("native_intro", "ca-app-pub-3940256099942544/2247696110");
        AdsVariable.banner_main = this.sharedPreferences.getString("banner_main", "ca-app-pub-3940256099942544/9214589741");
        AdsVariable.purchase_avoid_continue_ads_online = this.sharedPreferences.getString("purchase_avoid_continue_ads_online", "1");
        AdsVariable.native_bg_color = this.sharedPreferences.getString("native_bg_color", "F8F8F8");
        AdsVariable.native_text_color_title = this.sharedPreferences.getString("native_text_color_title", "875afe");
        AdsVariable.native_text_color_body = this.sharedPreferences.getString("native_text_color_body", "000000");
        AdsVariable.native_button_color = this.sharedPreferences.getString("native_button_color", "875afe");
        AdsVariable.native_button_text_color = this.sharedPreferences.getString("native_button_text_color", "FFFFFF");
        AdsVariable.native_ad_tag_bg = this.sharedPreferences.getString("native_ad_tag_bg", "875afe");
        AdsVariable.native_ad_tag_text = this.sharedPreferences.getString("native_ad_tag_text", "E6FEFB");
        AdsVariable.premiumScreen = this.sharedPreferences.getString("premiumScreen", "0");

        AdsVariable.rewordwatermark = this.sharedPreferences.getString("Rewordwatermark", "ca-app-pub-3940256099942544/5224354917");
        AdsVariable.rewordtheme = this.sharedPreferences.getString("Rewordtheme", "ca-app-pub-3940256099942544/5354046379");

        myEdit.putString("ads_dialog", AdsVariable.ads_dialog);
        myEdit.putString("ads_load_all", AdsVariable.ads_load_all);
        myEdit.putString("fullscreen_preload_high", AdsVariable.fullscreen_preload_high);
        myEdit.putString("fullscreen_preload_medium", AdsVariable.fullscreen_preload_medium);
        myEdit.putString("fullscreen_preload_normal", AdsVariable.fullscreen_preload_normal);
        myEdit.putString("appopen", AdsVariable.appopen);
        myEdit.putString("appopenSplash", AdsVariable.appopenSplash);

        myEdit.putString("premiumScreen", AdsVariable.premiumScreen);

        myEdit.putString("full_Splash_Activity_high", AdsVariable.full_Splash_Activity_high);
        myEdit.putString("full_Splash_Activity_normal", AdsVariable.full_Splash_Activity_normal);

        myEdit.putString("fullscreen_purchase", AdsVariable.fullscreen_purchase);


        myEdit.putString("native_language_high", AdsVariable.native_language_high);
        myEdit.putString("native_language", AdsVariable.native_language);
        myEdit.putString("native_intro_high", AdsVariable.native_intro_high);
        myEdit.putString("native_intro", AdsVariable.native_intro);

        myEdit.putString("banner_main", AdsVariable.banner_main);

        myEdit.putString("purchase_avoid_continue_ads_online", AdsVariable.purchase_avoid_continue_ads_online);
        myEdit.putString("native_bg_color", AdsVariable.native_bg_color);
        myEdit.putString("native_text_color_title", AdsVariable.native_text_color_title);
        myEdit.putString("native_text_color_body", AdsVariable.native_text_color_body);

        myEdit.putString("native_button_color", AdsVariable.native_button_color);
        myEdit.putString("native_button_text_color", AdsVariable.native_button_text_color);
        myEdit.putString("native_ad_tag_bg", AdsVariable.native_ad_tag_bg);
        myEdit.putString("native_ad_tag_text", AdsVariable.native_ad_tag_text);
        myEdit.putString("Rewordwatermark", AdsVariable.rewordwatermark);
        myEdit.putString("Rewordtheme", AdsVariable.rewordtheme);
        myEdit.commit();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                next();
            }
        });

    }



    
    void next() {
        ConsentRequestParameters build = new ConsentRequestParameters.Builder().setTagForUnderAgeOfConsent(false).build();
        ConsentInformation consentInformation = UserMessagingPlatform.getConsentInformation(this.activity);
        this.consentInformation = consentInformation;
        consentInformation.requestConsentInfoUpdate(this.activity, build, new ConsentInformation.OnConsentInfoUpdateSuccessListener() {
            @Override
            public void onConsentInfoUpdateSuccess() {
                UserMessagingPlatform.loadAndShowConsentFormIfRequired(activity, new ConsentForm.OnConsentFormDismissedListener() {
                    @Override
                    public void onConsentFormDismissed(FormError formError) {
                        m266x92e55189(formError);
                    }
                });
            }
        }, new ConsentInformation.OnConsentInfoUpdateFailureListener() {
            @Override
            public void onConsentInfoUpdateFailure(FormError formError) {
                initializeMobileAdsSdk();
            }
        });

    }
    public  void m266x92e55189(FormError formError) {
        if (formError == null) {
            if (consentInformation.canRequestAds()) {
                initializeMobileAdsSdk();
                return;
            }
            return;
        }
        initializeMobileAdsSdk();
    }

    public void initializeMobileAdsSdk() {
        try {
            MobileAds.initialize(this.activity, new OnInitializationCompleteListener() {
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                    List<String> testDevices = new ArrayList<>();
                    testDevices.add(AdRequest.DEVICE_ID_EMULATOR);
                    testDevices.add("C6E111295754E60AA2859AD2850CD902");


                    RequestConfiguration requestConfiguration
                            = new RequestConfiguration.Builder().setTestDeviceIds(testDevices).build();
                    MobileAds.setRequestConfiguration(requestConfiguration);

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            goToMain();
                        }
                    }, 2000);
                }
            });
        } catch (Exception unused) {
            goToMain();
        }
    }

    public void goToMain() {
        if (isNetworkAvailable()) {
            this.splashInterface.callNativePreload();
            this.secondsRemaining = 0;
            if (!MyApplication.appOpenAdManager.isAdAvailable()) {
                MyApplication.appOpenAdManager.loadAd(this.activity);
            }
            if (!AdsVariable.appopenSplash.equalsIgnoreCase("11")) {
                createTimer();
            } else {
                this.activity.runOnUiThread(new Runnable() {
                    public void run() {
                        adsLoadUtil.callAdMobAdsSplash(AdsVariable.full_Splash_Activity_high, activity, new AdsLoadUtil.FullscreenAds() {
                            public void nextActivity() {
                                nextactivityopen();
                            }

                            public void loadToFail() {
                                adsLoadUtil.callAdMobAdsSplash(AdsVariable.full_Splash_Activity_normal, activity, new AdsLoadUtil.FullscreenAds() {
                                    public void nextActivity() {
                                        nextactivityopen();
                                    }

                                    public void loadToFail() {
                                        nextactivityopen();
                                    }

                                    public void changeFlag() {
                                        AdsVariable.purchaseFlag = 1;
                                    }
                                });
                            }

                            public void changeFlag() {
                                AdsVariable.purchaseFlag = 1;
                            }
                        });
                    }
                });
            }
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    nextactivityopen();
                }
            }, 2500);
        }
    }

    private void createTimer() {
        final long j2 = 30;
        new CountDownTimer((long) 30 * 1000, 1000) {
            public void onTick(long j) {
                 secondsRemaining = (j / 1000) + 1;
                if (secondsRemaining >= j2 || isNetworkAvailable()) {
                    if (secondsRemaining < j2) {

                        if (MyApplication.appOpenAdManager.isFaildLoadingAd()) {
                            AdsVariable.needToShow = false;
                            MyApplication myApplication2 = (MyApplication) activity.getApplication();
                            MyApplication.appOpenAdManager.isFaildLoadingAd = false;
                            adsLoadUtil.callAdMobAdsSplash(AdsVariable.full_Splash_Activity_high, activity, new AdsLoadUtil.FullscreenAds() {
                                public void nextActivity() {
                                    nextactivityopen();
                                }

                                public void loadToFail() {
                                    adsLoadUtil.callAdMobAdsSplash(AdsVariable.full_Splash_Activity_normal, activity, new AdsLoadUtil.FullscreenAds() {
                                        public void nextActivity() {
                                            nextactivityopen();
                                        }

                                        public void loadToFail() {
                                            nextactivityopen();
                                        }

                                        public void changeFlag() {
                                            AdsVariable.purchaseFlag = 1;
                                        }
                                    });
                                }

                                public void changeFlag() {
                                    AdsVariable.purchaseFlag = 1;
                                }
                            });
                            cancel();
                            return;
                        }
                    }
                    if (secondsRemaining < j2) {

                        if (MyApplication.appOpenAdManager.isAdAvailable()) {
                            Application application = activity.getApplication();
                            if (!(application instanceof MyApplication)) {
                                AdsVariable.needToShow = false;
                                MyApplication myApplication4 = (MyApplication) activity.getApplication();
                                if (MyApplication.appOpenAdManager.isFaildLoadingAd()) {
                                    adsLoadUtil.callAdMobAdsSplash(AdsVariable.full_Splash_Activity_high, activity, new AdsLoadUtil.FullscreenAds() {
                                        public void nextActivity() {
                                            nextactivityopen();
                                        }

                                        public void loadToFail() {
                                            adsLoadUtil.callAdMobAdsSplash(AdsVariable.full_Splash_Activity_normal, activity, new AdsLoadUtil.FullscreenAds() {
                                                public void nextActivity() {
                                                    nextactivityopen();
                                                }

                                                public void loadToFail() {
                                                    nextactivityopen();
                                                }

                                                public void changeFlag() {
                                                    AdsVariable.purchaseFlag = 1;
                                                }
                                            });
                                        }

                                        public void changeFlag() {
                                            AdsVariable.purchaseFlag = 1;
                                        }
                                    });
                                } else {
                                    nextactivityopen();
                                }
                                cancel();
                                return;
                            }

                            if (MyApplication.appOpenAdManager.isFaildLoadingAd()) {
                                adsLoadUtil.callAdMobAdsSplash(AdsVariable.full_Splash_Activity_high, activity, new AdsLoadUtil.FullscreenAds() {
                                    public void nextActivity() {
                                        nextactivityopen();
                                    }

                                    public void loadToFail() {
                                        adsLoadUtil.callAdMobAdsSplash(AdsVariable.full_Splash_Activity_normal, activity, new AdsLoadUtil.FullscreenAds() {
                                            public void nextActivity() {
                                                nextactivityopen();
                                            }

                                            public void loadToFail() {
                                                nextactivityopen();
                                            }

                                            public void changeFlag() {
                                                AdsVariable.purchaseFlag = 1;
                                            }
                                        });
                                    }

                                    public void changeFlag() {
                                        AdsVariable.purchaseFlag = 1;
                                    }
                                });
                                return;
                            }
                            new AdsPreloadUtils(activity).callPreloadInterstitialDouble(AdsVariable.fullscreen_preload_high, AdsVariable.fullscreen_preload_normal);
                            ((MyApplication) application).showAdIfAvailable(activity, new MyApplication.OnShowAdCompleteListener() {
                                public void onShowAdComplete() {
                                    AdsVariable.needToShow = false;
                                    nextactivityopen();
                                }
                            });
                            cancel();
                            return;
                        }
                        return;
                    }
                    return;
                }
                AdsVariable.needToShow = false;
                adsLoadUtil.callAdMobAdsSplash(AdsVariable.full_Splash_Activity_high, activity, new AdsLoadUtil.FullscreenAds() {
                    public void nextActivity() {
                        nextactivityopen();
                    }

                    public void loadToFail() {
                        adsLoadUtil.callAdMobAdsSplash(AdsVariable.full_Splash_Activity_normal, activity, new AdsLoadUtil.FullscreenAds() {
                            public void nextActivity() {
                                nextactivityopen();
                            }

                            public void loadToFail() {
                                nextactivityopen();
                            }

                            public void changeFlag() {
                                AdsVariable.purchaseFlag = 1;
                            }
                        });
                    }

                    public void changeFlag() {
                        AdsVariable.purchaseFlag = 1;
                    }
                });
                cancel();
            }

            public void onFinish() {
                 secondsRemaining = 0;
                Application application = activity.getApplication();
                if (!(application instanceof MyApplication)) {
                    AdsVariable.needToShow = false;
                    nextactivityopen();
                    return;
                }
                MyApplication myApplication = (MyApplication) activity.getApplication();
                if (MyApplication.appOpenAdManager.isFaildLoadingAd()) {
                    adsLoadUtil.callAdMobAdsSplash(AdsVariable.full_Splash_Activity_high, activity, new AdsLoadUtil.FullscreenAds() {
                        public void nextActivity() {
                            nextactivityopen();
                        }

                        public void loadToFail() {
                            adsLoadUtil.callAdMobAdsSplash(AdsVariable.full_Splash_Activity_normal, activity, new AdsLoadUtil.FullscreenAds() {
                                public void nextActivity() {
                                    nextactivityopen();
                                }

                                public void loadToFail() {
                                    nextactivityopen();
                                }

                                public void changeFlag() {
                                    AdsVariable.purchaseFlag = 1;
                                }
                            });
                        }

                        public void changeFlag() {
                            AdsVariable.purchaseFlag = 1;
                        }
                    });
                    return;
                }
                new AdsPreloadUtils(activity).callPreloadInterstitialDouble(AdsVariable.fullscreen_preload_high, AdsVariable.fullscreen_preload_normal);
                ((MyApplication) application).showAdIfAvailable(activity, new MyApplication.OnShowAdCompleteListener() {
                    public void onShowAdComplete() {
                        AdsVariable.needToShow = false;
                        nextactivityopen();
                    }
                });
            }
        }.start();
    }

    
     public void nextactivityopen() {
        this.splashInterface.nextActivity();
    }

    
    public boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.activity.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
