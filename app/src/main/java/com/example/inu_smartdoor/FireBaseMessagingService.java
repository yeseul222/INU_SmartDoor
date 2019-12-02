package com.example.inu_smartdoor;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;



public class FireBaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (true) {
            } else {
                handleNow();
            }
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
    }

    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    private void sendNotification(String messageTitle, String messageBody) {
        Intent intent = new Intent(this, webcam.class); //Webcam.class로 연결되는 Intent 생성
        // 호출하는 액티비티가 스택에 존재할 경우에, 해당 액티비티를 최상위로 올리면서, 그 위에 존재하던 액티비티들은 모두 삭제
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //Notification 클릭시 Intent 처리됨 = Activity 실행
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT); //FLAG_ONE_SHOT(일회용)

        String channelId = getString(R.string.default_notification_channel_id);
        Bitmap mLargeIconForNoti = BitmapFactory.decodeResource(getResources(), R.mipmap.logo2);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); //Push 알림 메세지 소리
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(messageTitle)  //Title, Body는 Python에서 받아옴
                        .setContentText(messageBody)    //하나의 Notification으로 다양한 알림 가능
                        .setAutoCancel(true)    //알림 터치시 자동 삭제
                        .setLargeIcon(mLargeIconForNoti)
                        .setSound(defaultSoundUri)  //알림 왔을 때 소리
                        .setContentIntent(pendingIntent)    //알림을 눌렀을 때 실행할 Intent 설정
                        .setDefaults(Notification.DEFAULT_ALL); //알림 왔을 때 진동

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0, notificationBuilder.build());
    }
}

