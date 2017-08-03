package com.xtelsolution.xmec.fcm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.xtelsolution.xmec.model.entity.Config;

/**
 * Created by vivhp on 7/28/2017
 */

public class xMecFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private String TAG = "xMecFirebaseInstanceIdService";

    @SuppressLint("LongLogTag")
    @Override
    public void onTokenRefresh() {
        //Get update InstacneID Token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, refreshedToken);

        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);

        // sending reg id to your server
        sendRegistrationTokenToServer(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param fcmToken The new token.
     */
    private void sendRegistrationTokenToServer(String fcmToken) {
        // Add custom implementation, as needed.
        // Send token to NIP Server
    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.apply();
    }
}
