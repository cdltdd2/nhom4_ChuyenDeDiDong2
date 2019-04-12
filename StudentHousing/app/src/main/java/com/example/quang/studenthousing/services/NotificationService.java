package com.example.quang.studenthousing.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.example.quang.studenthousing.R;
import com.example.quang.studenthousing.object.NotificationHelper;

public class NotificationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_channel_01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setSmallIcon(R.drawable.icon_noti)
                    .setContentText("Service is running!").build();

            startForeground(1, notification);}
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {


        NotificationHelper notificationHelper = new NotificationHelper(this);
        notificationHelper.createNotification(getString(R.string.app_name)
                , getString(R.string.new_booking));

        return START_NOT_STICKY;
    }
}
