package com.example.quang.studenthousing.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class AppService extends Service
{

    private Thread thread;
    private boolean isRunning;


    @Override
    public void onCreate()
    {
        super.onCreate();

        thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {

            }
        });
    }

    private void update(int id)
    {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        thread.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

//    tu them vao khi implement
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
