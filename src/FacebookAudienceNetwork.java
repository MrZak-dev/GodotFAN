package org.godotengine.godot;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import com.godot.game.R;
import javax.microedition.khronos.opengles.GL10;
import com.facebook.ads.*;
import android.util.Log;



public class FacebookAudienceNetwork extends Godot.SingletonBase {

    protected Activity appActivity;
    protected Context appContext;
    //private int instanceId = 0;
    private InterstitialAd myInterstitialAd;

    public void InterstitiaLoader(){
        myInterstitialAd = new InterstitialAd(this.appContext,"285548149034555_285553599034010");
        myInterstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e("FAN", "Interstitial ad displayed.");
            }
            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e("FAN", "Interstitial ad dismissed.");
            }
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e("FAN", "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d("FAN", "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                myInterstitialAd.show();
            }
            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d("FAN", "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d("FAN", "Interstitial ad impression logged!");
            }
        });
    
    }

    
    public int add(int a , int b) {
        // a function to bind
        return a+b;
    }

    /*public void getInstanceId(int pInstanceId) {
        // You will need to call this method from Godot and pass in the get_instance_id().
        instanceId = pInstanceId;
    }*/

    static public Godot.SingletonBase initialize(Activity p_activity) {
        return new FacebookAudienceNetwork(p_activity);
    }

    public FacebookAudienceNetwork(Activity p_activity) {
        //register class name and functions to bind
        registerClass("FacebookAudienceNetwork", new String[]{"add","InterstitiaLoader"});
        this.appActivity = p_activity;
        this.appContext = appActivity.getApplicationContext();
        // you might want to try initializing your singleton here, but android
        // threads are weird and this runs in another thread, so to interact with Godot you usually have to do
        
    }

}