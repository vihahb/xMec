package com.xtelsolution.xmec;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Base64;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.FirebaseApp;
import com.xtel.nipservicesdk.NipApplication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.fabric.sdk.android.Fabric;

/**
 * Created by HUNGNT on 1/17/2017.
 */

public class MyApplication extends MultiDexApplication {
    private static final String TAG = "MyApplication";
    public static Context context;
    public static String PACKAGE_NAME;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
//        Fabric.with(this, new Crashlytics());
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AccountKit.initialize(getApplicationContext());
//        MultiDex.install(this);
        NipApplication.context = this;
        context = this;
        FirebaseApp.initializeApp(this);
        PACKAGE_NAME = context.getPackageName();
//        getKeyHash(PACKAGE_NAME);
        Log.v(TAG, "Pkg name " + PACKAGE_NAME);
//        new HtmlLoader(new LoadHtmlDetailListener() {
//            @Override
//            public void onPrepare() {
//
//            }
//
//            @Override
//            public void onSucess(Document result) {
//                IllnessTemple temple = IllnessTemple.fromDocument(result);
//                xLog.d(TAG, "onCreate: onSucess: " + temple.getName() + " : " + temple.getDetail());
//            }
//
//            @Override
//            public void onError() {
//
//            }
//        }).execute("http://diendan.songkhoe.vn/chi-tiet-trieu-chung-benh-khop-do-than-kinh-s2528-1265-470229.html");
    }

    private void getKeyHash(String pkg_name) {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(pkg_name,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
