package com.xtelsolution.xmec.fcm;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Base64Helper;
import com.xtelsolution.xmec.model.entity.DataPayloadEntity;
import com.xtelsolution.xmec.views.activity.NotificationActivity;

import java.util.Map;

/**
 * Created by vivhp on 7/28/2017
 */

public class xMecFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "xMecFirebaseMessagingSe";

    private NotificationHelper notificationUtils;

    @SuppressLint("LongLogTag")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.e(TAG, "From: " + remoteMessage.toString());
//        Log.e(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

//        sendNotification(remoteMessage.getNotification().getBody());

        if (remoteMessage == null)
            return;

//        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
//            handleNotification(remoteMessage.getNotification().getBody());
//        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData());

            Map<String, String> map = remoteMessage.getData();
            DataPayloadEntity payloadEntity = new DataPayloadEntity();
            payloadEntity.setName(map.get("name"));
            payloadEntity.setType(Integer.parseInt(map.get("type")));
            payloadEntity.setUid(Integer.parseInt(map.get("uid")));

            String names = Base64Helper.getDecode(payloadEntity.getName());

            Log.e("Dât object", JsonHelper.toJson(payloadEntity));

//            String title = payloadEntity.getTitle();
            String title = "";
            String message;

            int type = payloadEntity.getType();

            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationUtils utils = new NotificationUtils(this);

//            Intent notifyIntentNegative = new Intent(this, LoginActivity.class);
//            PendingIntent pendingIntentNegative = PendingIntent.getActivity(this, 11, notifyIntentNegative, PendingIntent.FLAG_UPDATE_CURRENT);

            Intent notifyIntentPositive = new Intent(this, NotificationActivity.class);
            PendingIntent pendingIntentPositive = PendingIntent.getActivity(this, 11, notifyIntentPositive, PendingIntent.FLAG_UPDATE_CURRENT);

            int id_notification = 0;
            id_notification++;
            switch (type) {
                case 1:
                    title = "Thông báo kết bạn";
                    message = names + " đã gửi cho bạn một lời mời kết bạn.";
                    utils.showSmallNotification(id_notification,
                            R.mipmap.ic_launcher,
                            title,
                            message,
                            //Negative Action Notification
                            -1, null, null,
                            //Positive Action Notification
                            R.mipmap.ic_arrow_right, "Tới thông báo", pendingIntentPositive,
                            notification
                    );

                    break;
                case 2:
                    title = "Thông báo kết bạn";
                    message = names + " đã chấp nhận lời mời kết bạn của bạn.";
                    utils.showSmallNotification(id_notification, R.mipmap.ic_launcher,
                            title,
                            message,
                            //Negative Action Notification
//                            R.mipmap.family_logo, "Go to Family", pendingIntentNegative,
                            -1, null, null,
                            //Positive Action Notification
                            R.mipmap.ic_arrow_right, "Go to Notification", pendingIntentPositive,
                            notification);
                    break;
            }


//                NotificationUtils notify = new NotificationUtils(MyApplication.context);
//                notify.showNotificationNomal(title,
//                        message,
//                        R.mipmap.ic_launcher,
//                        //Negative Action Notification
//                        R.mipmap.family_logo, "Go to Family", pendingIntentNegative,
//                        //Positive Action Notification
//                        R.mipmap.ic_arrow_right, "Go to Notification", pendingIntentPositive
//                );
//            handleDataMessage(payloadEntity);


//            if (type == 1) {

//            try {
//                JSONObject json = new JSONObject(remoteMessage.getData().toString());
//
//            } catch (Exception e) {
//                Log.e(TAG, "Exception: " + e.getMessage());
//            }
//        }
        }
    }
}

//    private void handleNotification(String message) {
//        if (!NotificationHelper.isAppIsInBackground(getApplicationContext())) {
//            // app is in foreground, broadcast the push message
//            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
//            pushNotification.putExtra("message", message);
//            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
//
//            // play notification sound
//            NotificationHelper notificationUtils = new NotificationHelper(getApplicationContext());
//            notificationUtils.playNotificationSound();
//        }else{
//            // If the app is in background, firebase itself handles the notification
//        }
//    }

//    private void handleDataMessage(DataPayloadEntity entity) {
//        try {
//            JSONObject dataObject = new JSONObject(dataString);
//            Log.e(TAG, "push json: " + dataObject.toString());
//
//            try {
//                JSONObject data = dataObject.getJSONObject("data");
//                DataPayloadEntity dataPayload = new DataPayloadEntity();
//                dataPayload.setMessage(data.getString("message"));
//                dataPayload.setTitle(data.getString("title"));
//                dataPayload.setType(data.getInt("type"));
//
//                Log.e(TAG, "title: " + dataPayload.getTitle());
//                Log.e(TAG, "message: " + dataPayload.getMessage());
//                Log.e(TAG, "payload: " + dataPayload.getType());
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Log.e(TAG, "Exception: " + e.getMessage());
//        }

//        try {
//            JSONObject data = json.getJSONObject("data");
//
//            String title = data.getString("title");
//            String message = data.getString("message");
//            boolean isBackground = data.getBoolean("is_background");
//            String imageUrl = data.getString("image");
//            String timestamp = data.getString("timestamp");
//            JSONObject payload = data.getJSONObject("payload");
//
//            Log.e(TAG, "title: " + title);
//            Log.e(TAG, "message: " + message);
//            Log.e(TAG, "isBackground: " + isBackground);
//            Log.e(TAG, "payload: " + payload.toString());
//            Log.e(TAG, "imageUrl: " + imageUrl);
//            Log.e(TAG, "timestamp: " + timestamp);
//
//
//            if (!NotificationHelper.isAppIsInBackground(getApplicationContext())) {
//                // app is in foreground, broadcast the push message
//                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
//                pushNotification.putExtra("message", message);
//                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
//
//                // play notification sound
//                NotificationHelper notificationUtils = new NotificationHelper(getApplicationContext());
//                notificationUtils.playNotificationSound();
//            } else {
//                // app is in background, show the notification in notification tray
//                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
//                resultIntent.putExtra("message", message);
//
//                // check for image attachment
//            }
//        } catch (JSONException e) {
//            Log.e(TAG, "Json Exception: " + e.getMessage());
//        } catch (Exception e) {
//            Log.e(TAG, "Exception: " + e.getMessage());
//        }
//}

/**
 * Showing notification with text only
 */
//    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
//        notificationUtils = new NotificationHelper(context);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
//    }
//
//    /**
//     * Showing notification with text and image
//     */
//    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
//        notificationUtils = new NotificationHelper(context);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
//    }
//
//    //This method is only generating push notification
//    //It is same as we did in earlier posts
//    private void sendNotification(String messageBody) {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("Firebase Push Notification")
//                .setContentText(messageBody)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0, notificationBuilder.build());
//    }
//}
