package org.godotengine.godot;

import android.app.Activity;
import android.content.Intent;
import com.godot.game.R;
import javax.microedition.khronos.opengles.GL10;
import com.facebook.ads.*;
import org.godotengine.godot.Utils;

public class AddModule extends Godot.SingletonBase {

    //protected Activity appActivity;
    //protected Context appContext;
    //private int instanceId = 0;
    private InterstitialAd myInterstitialAd;

    myInterstitialAd = new InterstitialAd(this,"285548149034555_285553599034010");

            myInterstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Utils.d("FB", "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                 interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        });

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd();

    public int add(int a , int b) {
        // a function to bind
        return a+b;
    }

    /*public void getInstanceId(int pInstanceId) {
        // You will need to call this method from Godot and pass in the get_instance_id().
        instanceId = pInstanceId;
    }*/

    static public Godot.SingletonBase initialize(Activity p_activity) {
        return new AddModule(p_activity);
    }

    public AddModule(Activity p_activity) {
        //register class name and functions to bind
        registerClass("AddModule", new String[]{"add"});
        //this.appActivity = p_activity;
        //this.appContext = appActivity.getApplicationContext();
        // you might want to try initializing your singleton here, but android
        // threads are weird and this runs in another thread, so to interact with Godot you usually have to do
        
    }

    // forwarded callbacks you can reimplement, as SDKs often need them
    /*
    protected void onMainActivityResult(int requestCode, int resultCode, Intent data) {}
    protected void onMainRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {}

    protected void onMainPause() {}
    protected void onMainResume() {}
    protected void onMainDestroy() {}

    protected void onGLDrawFrame(GL10 gl) {}
    protected void onGLSurfaceChanged(GL10 gl, int width, int height) {} // singletons will always miss first onGLSurfaceChanged call
    */
}