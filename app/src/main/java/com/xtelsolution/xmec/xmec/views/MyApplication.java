package com.xtelsolution.xmec.xmec.views;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Base64;
import android.util.Log;

import com.xtelsolution.xmec.callbacks.NewsHtmlLoader;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.listener.LoadNewsDetailListener;
import com.xtelsolution.xmec.model.entity.IllnessTemple;

import org.jsoup.nodes.Document;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
//        Fabric.with(this, new Crashlytics());
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AccountKit.initialize(getApplicationContext());
        MultiDex.install(this);
        context = this;
        PACKAGE_NAME = context.getPackageName();
//        getKeyHash(PACKAGE_NAME);
        Log.v("Pkg name", PACKAGE_NAME);
        new NewsHtmlLoader(new LoadNewsDetailListener() {
            @Override
            public void onPrepare() {

            }

            @Override
            public void onSucess(Document result) {
                IllnessTemple temple = IllnessTemple.fromDocument(result);
                xLog.d(TAG, "onCreate: onSucess: " + temple.getName() + " : " + temple.getDetail());
            }

            @Override
            public void onError() {

            }
        }).execute("http://diendan.songkhoe.vn/chi-tiet-trieu-chung-benh-khop-do-than-kinh-s2528-1265-470229.html");
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


}
