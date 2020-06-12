package org.godotengine.godot;

import org.godotengine.godot.*;
import com.godot.game.R;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;

import javax.microedition.khronos.opengles.GL10;
import com.facebook.ads.*;
import android.util.Log;



public class GodotFAN extends Godot.SingletonBase {

    protected Activity appActivity;
    protected Context appContext;
    private int instanceId = 0;
    private InterstitialAd interstitialAd; //interstitial ad
    private RewardedVideoAd rewardedVideoAd; // rewarded video ad 

    //Ads Sinals
    private boolean interstitialAdLoadingStatus = false;
    private boolean rewardedVideoAdLoadingStatus = false;

    public void FacebookAdsInit(final int instanceId,final String interstitialAdId , final String rewardedVideoAdId){

        this.instanceId = instanceId;
        
        AudienceNetworkAds.initialize(this.appContext);// Initializing the audience network
        interstitialAd = new InterstitialAd(this.appContext,interstitialAdId); //initialization of interstitial with placement id given from godot script
        rewardedVideoAd = new RewardedVideoAd(this.appContext,rewardedVideoAdId); // initialization of the rewarded video ad with placement id given from godot script
        interstitialAd.loadAd();
        rewardedVideoAd.loadAd();

        appActivity.runOnUiThread(new Runnable() {
            public void run() {

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
                        interstitialAd.loadAd(); // in case of errors try to load again
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

                //Rewarded video ad listener
                rewardedVideoAd.setAdListener(new RewardedVideoAdListener() {
                    @Override
                    public void onError(final Ad ad, final AdError error) {
                      // Rewarded video ad failed to load
                      Log.e("FAN", "Rewarded video ad failed to load: " + error.getErrorMessage());
                      rewardedVideoAd.loadAd(); // in cqse of errors try to load again
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
                      Log.d("FAN", "Rewarded video completed!");
                      GodotLib.calldeferred(instanceId, "onRewardedCompleted", new Object[]{});
                      // Call method to give reward
                      // giveReward();
                    }
                
                    @Override
                    public void onRewardedVideoClosed() {
                      // The Rewarded Video ad was closed - this can occur during the video
                      // by closing the app, or closing the end card.
                      Log.d("FAN", "Rewarded video ad closed!");
                      GodotLib.calldeferred(instanceId, "onRewardedClosed", new Object[]{});
                      rewardedVideoAd.destroy(); // i couldn't load another rewardedVideo without destroying the old one 
                      rewardedVideoAd.loadAd(); // load the rewarded video , so we can use it again.
                    }
                });
            }
        });
    }
    public void showInterstitial(){
        if(interstitialAdLoadingStatus){
            interstitialAd.show();
            interstitialAdLoadingStatus = false; //reset the signal to false so the Adloaded signal from the ad listener will set it to true again when ad is loaded
        }else{
            Log.d("GodotFan","Interstitial is not Loaded yet !");
        }
    }

    public void showRewardedVideo(){
        if(rewardedVideoAdLoadingStatus){
            rewardedVideoAd.show();
            rewardedVideoAdLoadingStatus = false; //reset the signal to false so the Adloaded signal from the ad listener will set it to true again when ad is loaded
        }else {
            Log.d("GodotFan","RewardedVideo is not Loaded yet !");
        }
    }

    /*public void getInstanceId(int pInstanceId) {
        // You will need to call this method from Godot and pass in the get_instance_id().
        instanceId = pInstanceId;
    }*/

    static public Godot.SingletonBase initialize(final Activity p_activity) {
        return new GodotFAN(p_activity);
    }

    public GodotFAN(final Activity p_activity) {
        //register class name and functions to bind
        registerClass("GodotFAN", new String[]{"FacebookAdsInit","showInterstitial","showRewardedVideo"});
        this.appActivity = p_activity;
        this.appContext = appActivity.getApplicationContext();
        // you might want to try initializing your singleton here, but android
        // threads are weird and this runs in another thread, so to interact with Godot you usually have to do
        
    }

}