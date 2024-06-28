package com.vishu.ads;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;


public class AdsNativeSmallUtils {
    
    public AdLoader adLoader;
    final Context context;
    public NativeAdView nativeAdView;

    public interface AdsInterface {
        void loadToFail();

        void nextActivity();
    }

    public AdsNativeSmallUtils(Context context2) {
        this.context = context2;
    }


    public void callAdMobNativeDouble(AdsPreloadUtils adsPreloadUtils, FrameLayout frameLayout, String str, String str2, final Activity activity, final AdsInterface adsInterface) {
        if (!isNetworkAvailable()) {
            adsInterface.loadToFail();
        } else if (!str.equalsIgnoreCase("11")) {
            NativeAdView nativeAdView2 = frameLayout.findViewById(R.id.ad_view);
            this.nativeAdView = nativeAdView2;
            nativeAdView2.setHeadlineView(nativeAdView2.findViewById(R.id.ad_headline));
            NativeAdView nativeAdView3 = this.nativeAdView;
            nativeAdView3.setBodyView(nativeAdView3.findViewById(R.id.ad_body));
            NativeAdView nativeAdView4 = this.nativeAdView;
            nativeAdView4.setCallToActionView(nativeAdView4.findViewById(R.id.ad_call_to_action));
            NativeAdView nativeAdView5 = this.nativeAdView;
            nativeAdView5.setIconView(nativeAdView5.findViewById(R.id.ad_icon));
            NativeAdView nativeAdView6 = this.nativeAdView;
            nativeAdView6.setStarRatingView(nativeAdView6.findViewById(R.id.ad_stars));
            NativeAdView nativeAdView7 = this.nativeAdView;
            nativeAdView7.setAdvertiserView(nativeAdView7.findViewById(R.id.ad_advertiser));
            NativeAdOptions build = new NativeAdOptions.Builder().setVideoOptions(new VideoOptions.Builder().setStartMuted(false).build()).build();
            if (adsPreloadUtils == null || adsPreloadUtils.smallNativeAdmob == null) {
                final String str3 = str2;
                final NativeAdOptions nativeAdOptions = build;
                AdLoader build2 = new AdLoader.Builder(this.context, str).withAdListener(new AdListener() {
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        AdLoader unused = AdsNativeSmallUtils.this.adLoader = new AdLoader.Builder(AdsNativeSmallUtils.this.context, str3).withAdListener(new AdListener() {
                            public void onAdFailedToLoad(LoadAdError loadAdError) {
                                super.onAdFailedToLoad(loadAdError);
                                activity.runOnUiThread(new Runnable() {
                                    public void run() {
                                        adsInterface.loadToFail();
                                    }
                                });
                            }
                        }).withNativeAdOptions(nativeAdOptions).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                            public void onNativeAdLoaded(NativeAd nativeAd) {
                                if (!AdsNativeSmallUtils.this.adLoader.isLoading()) {
                                    AdsNativeSmallUtils.this.setAdmobNativeLayout(activity, nativeAd, adsInterface);
                                }
                            }
                        }).build();
                        AdsNativeSmallUtils.this.adLoader.loadAd(new AdRequest.Builder().build());
                    }
                }).withNativeAdOptions(build).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        if (!AdsNativeSmallUtils.this.adLoader.isLoading()) {
                            AdsNativeSmallUtils.this.setAdmobNativeLayout(activity, nativeAd, adsInterface);
                        }
                    }
                }).build();
                this.adLoader = build2;
                build2.loadAd(new AdRequest.Builder().build());
                return;
            }
            setAdmobNativeLayout(activity, adsPreloadUtils.smallNativeAdmob, adsInterface);
        } else {
            adsInterface.loadToFail();
        }
    }

    public void setAdmobNativeLayout(Activity activity, NativeAd nativeAd, AdsInterface adsInterface) {
        nativeAd.getMediaContent().getVideoController().setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
            public void onVideoEnd() {
                super.onVideoEnd();
            }
        });
        ((TextView) this.nativeAdView.getHeadlineView()).setText(nativeAd.getHeadline());
        ((TextView) this.nativeAdView.getBodyView()).setText(nativeAd.getBody());
        ((Button) this.nativeAdView.getCallToActionView()).setText(nativeAd.getCallToAction());
        try {
            this.nativeAdView.findViewById(R.id.ad_attribution).setBackground(getRoundRect());
            ((TextView) this.nativeAdView.findViewById(R.id.ad_attribution)).setTextColor(Color.parseColor("#" + AdsVariable.native_ad_tag_text));
            ((TextView) this.nativeAdView.getHeadlineView()).setTextColor(Color.parseColor("#" + AdsVariable.native_text_color_title));
            ((TextView) this.nativeAdView.getAdvertiserView()).setTextColor(Color.parseColor("#" + AdsVariable.native_text_color_body));
            ((TextView) this.nativeAdView.getBodyView()).setTextColor(Color.parseColor("#" + AdsVariable.native_text_color_body));
            View callToActionView = this.nativeAdView.getCallToActionView();
            callToActionView.setBackgroundColor(Color.parseColor("#" + AdsVariable.native_button_color));
            ((AppCompatButton) this.nativeAdView.findViewById(R.id.ad_call_to_action)).setTextColor(Color.parseColor("#" + AdsVariable.native_button_text_color));
        } catch (RuntimeException unused) {
            this.nativeAdView.findViewById(R.id.ad_attribution).setBackground(getRoundRect());
            ((TextView) this.nativeAdView.findViewById(R.id.ad_attribution)).setTextColor(Color.parseColor("#" + AdsVariable.native_ad_tag_text));
            ((TextView) this.nativeAdView.getHeadlineView()).setTextColor(Color.parseColor("#" + AdsVariable.native_text_color_title));
            ((TextView) this.nativeAdView.getAdvertiserView()).setTextColor(Color.parseColor("#" + AdsVariable.native_text_color_body));
            ((TextView) this.nativeAdView.getBodyView()).setTextColor(Color.parseColor("#" + AdsVariable.native_text_color_body));
            View callToActionView2 = this.nativeAdView.getCallToActionView();
            callToActionView2.setBackgroundColor(Color.parseColor("#" + AdsVariable.native_button_color));
            ((AppCompatButton) this.nativeAdView.findViewById(R.id.ad_call_to_action)).setTextColor(Color.parseColor("#" + AdsVariable.native_button_text_color));
        } catch (Exception unused2) {
            this.nativeAdView.findViewById(R.id.ad_attribution).setBackground(getRoundRectCatch(activity));
            ((TextView) this.nativeAdView.findViewById(R.id.ad_attribution)).setTextColor(activity.getResources().getColor(R.color.native_ad_tag_text));
            ((TextView) this.nativeAdView.getHeadlineView()).setTextColor(activity.getResources().getColor(R.color.native_text_color_title));
            ((TextView) this.nativeAdView.getAdvertiserView()).setTextColor(activity.getResources().getColor(R.color.native_text_color_body));
            ((TextView) this.nativeAdView.getBodyView()).setTextColor(activity.getResources().getColor(R.color.native_text_color_body));
            this.nativeAdView.getCallToActionView().setBackgroundColor(activity.getResources().getColor(R.color.native_button_color));
            ((AppCompatButton) this.nativeAdView.findViewById(R.id.ad_call_to_action)).setTextColor(activity.getResources().getColor(R.color.native_button_text_color));
        }
        NativeAd.Image icon = nativeAd.getIcon();
        if (icon == null) {
            this.nativeAdView.getIconView().setVisibility(View.INVISIBLE);
        } else {
            ((ImageView) this.nativeAdView.getIconView()).setImageDrawable(icon.getDrawable());
            this.nativeAdView.getIconView().setVisibility(View.VISIBLE);
        }
        if (nativeAd.getStarRating() == null) {
            this.nativeAdView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) this.nativeAdView.getStarRatingView()).setRating(nativeAd.getStarRating().floatValue());
            this.nativeAdView.getStarRatingView().setVisibility(View.VISIBLE);
        }
        if (nativeAd.getAdvertiser() == null) {
            this.nativeAdView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) this.nativeAdView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            this.nativeAdView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
        this.nativeAdView.setNativeAd(nativeAd);
        adsInterface.nextActivity();
    }

    public Drawable getRoundRectForBg() {
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{30.0f, 30.0f, 0.0f, 0.0f, 30.0f, 30.0f, 30.0f, 30.0f}, null, null));
        Paint paint = shapeDrawable.getPaint();
        paint.setColor(Color.parseColor("#" + AdsVariable.native_bg_color));
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable.getPaint().setAntiAlias(true);
        shapeDrawable.getPaint().setFlags(1);
        return shapeDrawable;
    }

    public Drawable getRoundRect() {
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{20.0f, 20.0f, 0.0f, 0.0f, 20.0f, 20.0f, 0.0f, 0.0f}, null, null));
        Paint paint = shapeDrawable.getPaint();
        paint.setColor(Color.parseColor("#" + AdsVariable.native_ad_tag_bg));
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable.getPaint().setAntiAlias(true);
        shapeDrawable.getPaint().setFlags(1);
        return shapeDrawable;
    }

    public Drawable getRoundRectCatch(Activity activity) {
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{20.0f, 20.0f, 0.0f, 0.0f, 20.0f, 20.0f, 0.0f, 0.0f}, null, null));
        shapeDrawable.getPaint().setColor(activity.getResources().getColor(R.color.native_ad_tag_bg));
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable.getPaint().setAntiAlias(true);
        shapeDrawable.getPaint().setFlags(1);
        return shapeDrawable;
    }

    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
