package com.example.quang.studenthousing.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        DataClient dataClient = APIClient.getData();
        Call<String> callBack = dataClient.updateCheckSeen(id);
        callBack.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                if ("true".equals(response.body())){
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                Toast.makeText(AppService.this, t.getMessage()+"", Toast.LENGTH_SHORT).show();
                Log.e("-------",t.getMessage());
            }

        });
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
