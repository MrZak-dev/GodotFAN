package org.godotengine.godot;

import org.godotengine.godot.*;
import com.godot.game.R;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.view.Gravity;
import android.view.View;

import javax.microedition.khronos.opengles.GL10;
import com.facebook.ads.*;



public class GodotFAN extends Godot.SingletonBase {

    private Activity appActivity = null;
    private Context appContext = null;
    private int instanceId = 0;
    private InterstitialAd interstitialAd; //interstitial ad
    private RewardedVideoAd rewardedVideoAd; // rewarded video ad 
    private AdView AdView; // banner Ad

    private FrameLayout layout = null;
    private FrameLayout.LayoutParams layoutParams = null;
    private LinearLayout linearLayout = null;
    private LinearLayout.LayoutParams linearLayoutParams = null;

    //Ids 
    private String rewardedVideoAdId;
    private String interstitialAdId;
    private String bannerAdId;

    //Ads Sinals
    private boolean interstitialAdLoadingStatus = false;
    private boolean rewardedVideoAdLoadingStatus = false;


    @Override
	public View onMainCreateView(Activity activity) {
		layout = new FrameLayout(activity);
		return layout;
	}

    public void FacebookAdsInit(final int instanceId,final String interstitialAdId , final String rewardedVideoAdId , final String bannerAdId){

        this.instanceId = instanceId;
        this.interstitialAdId = interstitialAdId;
        this.rewardedVideoAdId = rewardedVideoAdId;
        this.bannerAdId = bannerAdId;
        
        AudienceNetworkAds.initialize(this.appContext);// Initializing the audience network
        Log.e("FAN", "Facebook Audience Network Initialize ");
        loadInterstitial();
    }


    public void loadInterstitial(){
        interstitialAd = new InterstitialAd(this.appContext,interstitialAdId); //initialization of interstitial with placement id given from godot script
        appActivity.runOnUiThread(new Runnable() {
            @Override public void run() {

                // interstitial ad listener
                interstitialAd.setAdListener(new InterstitialAdListener() {
                    @Override
                    public void onInterstitialDisplayed(final Ad ad) {
                        // Interstitial ad displayed callback
                        Log.e("FAN", "Interstitial ad displayed.");
                    }
                    
                    @Override
                    public void onInterstitialDismissed(final Ad ad) {
                        // Interstitial dismissed callback
                        Log.e("FAN", "Interstitial ad dismissed.");
                        GodotLib.calldeferred(instanceId, "onInterstitialClosed", new Object[]{});
                        interstitialAd.loadAd(); // load the interstitial , so we can use it again.
                    }
                    @Override
                    public void onError(final Ad ad, final AdError adError) {
                        // Ad error callback
                        Log.e("FAN", "Interstitial ad failed to load: " + adError.getErrorMessage());
                    }
        
                    @Override
                    public void onAdLoaded(final Ad ad) {
                        // Interstitial ad is loaded and ready to be displayed
                        Log.d("FAN", "Interstitial ad is loaded and ready to be displayed!");
                        GodotLib.calldeferred(instanceId, "onInterstitialReady", new Object[]{});
                        // switch lodaing signal to true
                        interstitialAdLoadingStatus = true;
                        
                    }
                    @Override
                    public void onAdClicked(final Ad ad) {
                        // Ad clicked callback
                        Log.d("FAN", "Interstitial ad clicked!");
                    }
                    @Override
                    public void onLoggingImpression(final Ad ad) {
                        // Ad impression logged callback
                        Log.d("FAN", "Interstitial ad impression logged!");
                    }
                }); 
                interstitialAd.loadAd();
                
            }
        });
    }

    public void loadRewardedVideo(){
        appActivity.runOnUiThread(new Runnable() {
            @Override public void run() {

                if(rewardedVideoAd != null){
                    rewardedVideoAd.destroy();
                    rewardedVideoAd = null;
                }
                
                rewardedVideoAd = new RewardedVideoAd(appContext,rewardedVideoAdId); // initialization of the rewarded video ad with placement id given from godot script
                rewardedVideoAd.setAdListener(new RewardedVideoAdListener() {
                    @Override
                    public void onError(final Ad ad, final AdError error) {
                      // Rewarded video ad failed to load
                      Log.e("FAN", "Rewarded video ad failed to load: " + error.getErrorMessage());
                    }
                
                    @Override
                    public void onAdLoaded(final Ad ad) {
                      // Rewarded video ad is loaded and ready to be displayed  
                      Log.d("FAN", "Rewarded video ad is loaded and ready to be displayed!");
                      GodotLib.calldeferred(instanceId, "onRewardedReady", new Object[]{});
                      // set loading signal to true
                      rewardedVideoAdLoadingStatus = true;
                    }
                
                    @Override
                    public void onAdClicked(final Ad ad) {
                      // Rewarded video ad clicked
                      Log.d("FAN", "Rewarded video ad clicked!");
                    }
                
                    @Override
                    public void onLoggingImpression(final Ad ad) {
                      // Rewarded Video ad impression - the event will fire when the 
                      // video starts playing
                      Log.d("FAN", "Rewarded video ad impression logged!");
                    }
                
                    @Override
                    public void onRewardedVideoCompleted() {
                      // Rewarded Video View Complete - the video has been played to the end.
                      // You can use this event to initialize your reward
                      // Call method to give reward
                      GodotLib.calldeferred(instanceId, "onRewardedCompleted", new Object[]{});
                      Log.d("FAN", "Rewarded video completed!"); 
                    }
                
                    @Override
                    public void onRewardedVideoClosed() {
                      // The Rewarded Video ad was closed - this can occur during the video
                      // by closing the app, or closing the end card.
                      GodotLib.calldeferred(instanceId, "onRewardedClosed", new Object[]{});
                      Log.d("FAN", "Rewarded video ad closed!");
                      
                    }
                });
                rewardedVideoAd.loadAd();
            }
        });
    }

    public void showInterstitial(){
        if(interstitialAdLoadingStatus){
            interstitialAd.show();
            interstitialAdLoadingStatus = false; //reset the signal to false so the Adloaded signal from the ad listener will set it to true again when ad is loaded
        }else{
            Log.d("FAN","Interstitial is not Loaded yet !");
        }
    }

    public void showRewardedVideo(){
        if(rewardedVideoAdLoadingStatus){
            rewardedVideoAd.show();
            rewardedVideoAdLoadingStatus = false; //reset the signal to false so the Adloaded signal from the ad listener will set it to true again when ad is loaded
        }else {
            Log.d("FAN","RewardedVideo is not Loaded yet !");
        }
    }

    public void loadBanner(final boolean isTop){
        int adPosition= isTop ? Gravity.TOP : Gravity.BOTTOM ;

        layoutParams =  new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT,
            adPosition
        );

        linearLayout = new LinearLayout(this.appContext);
        linearLayoutParams =  new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 
            LinearLayout.LayoutParams.WRAP_CONTENT
        );

        AdView = new AdView(this.appContext, this.bannerAdId , AdSize.BANNER_HEIGHT_50);

        appActivity.runOnUiThread(new Runnable() {
            @Override public void run() {
                layout.setLayoutParams(layoutParams);
                linearLayout.setLayoutParams(linearLayoutParams);
                linearLayout.addView(AdView);
                layout.addView(linearLayout);
                
                AdView.loadAd();

                AdView.setAdListener(new AdListener() {
                    @Override
                    public void onError(Ad ad, AdError adError) {
                        // Ad error callback
                        Log.e("FAN", "Banner ad failed to load: " + adError.getErrorMessage());
                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        // Ad loaded callback
                        Log.d("FAN","Banner is Loaded !");
                    }

                    @Override
                    public void onAdClicked(Ad ad) {
                        // Ad clicked callback
                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {
                        Log.d("FAN","Banner Impression logged !");
                    }
                });

            }
        });
        
        Log.d("FAN","Load Banner!");
    }

    public void showBanner(){
        appActivity.runOnUiThread(new Runnable()
		{
			@Override public void run()
			{
				if (AdView.getVisibility() == View.VISIBLE) return;
                AdView.loadAd();
				AdView.setVisibility(View.VISIBLE);
				Log.d("FAN", " Show Banner");
			}
		});
    }

    public void hideBanner(){
        appActivity.runOnUiThread(new Runnable()
		{
			@Override public void run()
			{
			    if (AdView.getVisibility() == View.INVISIBLE) return;
				AdView.setVisibility(View.INVISIBLE);
				Log.d("FAN", " Hide Banner");
			}
		});
    }


    static public Godot.SingletonBase initialize(final Activity p_activity) {
        return new GodotFAN(p_activity);
    }

    public GodotFAN(final Activity p_activity) {
        //register class name and functions to bind
        registerClass("GodotFAN", new String[]{
            "FacebookAdsInit",
            "showInterstitial",
            "showRewardedVideo",
            "loadRewardedVideo",
            "loadBanner",
            "showBanner",
            "hideBanner"
        });
        appActivity = p_activity;
        appContext = p_activity.getApplicationContext();
        // you might want to try initializing your singleton here, but android
        // threads are weird and this runs in another thread, so to interact with Godot you usually have to do
        
    }

}