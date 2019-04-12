package org.godotengine.godot;

import android.app.Activity;
import android.content.Intent;
import com.godot.game.R;
import javax.microedition.khronos.opengles.GL10;
import com.facebook.ads.*;


public class FacebookAudienceNetwork extends Godot.SingletonBase {

    //protected Activity appActivity;
    //protected Context appContext;
    //private int instanceId = 0;
    //private InterstitialAd myInterstitialAd;

    //myInterstitialAd = new InterstitialAd(this,"285548149034555_285553599034010");

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
        registerClass("FacebookAudienceNetwork", new String[]{"add"});
        //this.appActivity = p_activity;
        //this.appContext = appActivity.getApplicationContext();
        // you might want to try initializing your singleton here, but android
        // threads are weird and this runs in another thread, so to interact with Godot you usually have to do
        
    }

}