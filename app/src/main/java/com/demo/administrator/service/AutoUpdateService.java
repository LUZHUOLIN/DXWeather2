package com.demo.administrator.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.demo.administrator.receiver.AutoUpdateReceiver;
import com.demo.administrator.util.HttpCallbackListener;
import com.demo.administrator.util.HttpUtil;
import com.demo.administrator.util.Utility;


public class AutoUpdateService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateWeather();
            }
        });
        AlarmManager alamManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int hour = 6 * 60 * 60 * 1000;
        Long triggerTime = SystemClock.elapsedRealtime() + hour;
        Intent in = new Intent(this, AutoUpdateReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, in, 0);
        alamManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather() {
        SharedPreferences perfs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherCode = perfs.getString("weather_Code", "");
        String address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode + ".html";
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Utility.handleWeatherResponse(AutoUpdateService.this, response);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });

    }
}
