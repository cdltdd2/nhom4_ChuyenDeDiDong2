package com.example.quang.studenthousing.services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.quang.studenthousing.object.IDBooking;

import java.util.ArrayList;
import java.util.List;

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
                while (true)
                {
                    SharedPreferences pre = getSharedPreferences("studenthousing", MODE_PRIVATE);
                    String user = pre.getString("user","");
                    if (!user.equalsIgnoreCase("")){
                        String[] arr = user.split("-");
                        int idUser = Integer.parseInt(arr[0]);
                        int permission = Integer.parseInt(arr[5]);

                        DataClient dataClient = APIClient.getData();
                        Call<List<IDBooking>> callBack = dataClient.checkNewBooking(idUser);
                        callBack.enqueue(new Callback<List<IDBooking>>()
                        {
                            @Override
                            public void onResponse(Call<List<IDBooking>> call, Response<List<IDBooking>> response)
                            {
                                if (!"none".equals(response.body())){

                                    ArrayList<IDBooking> arrID = (ArrayList<IDBooking>) response.body();
                                    for (int i=0; i<arrID.size(); i++){
                                        update(arrID.get(i).getIDBOOK());
                                    }

                                    Intent intent = new Intent(AppService.this,NotificationService.class);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        startForegroundService(intent);
                                    }
                                    else {
                                        startService(intent);
                                    }

                                }
                            }

                            @Override
                            public void onFailure(Call<List<IDBooking>> call, Throwable t) {
                            }
                        });
                    }
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
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
