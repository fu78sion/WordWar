package com.example.wordwar.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

public class MyService extends Service {

    private MediaPlayer mediaPlayer;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent.getStringExtra("query") != null) {
            String query = intent.getStringExtra("query");
            Uri location = Uri.parse(query);
            mediaPlayer = MediaPlayer.create(this,location);

                mediaPlayer.start();
            Log.d("myService", "onStartCommand: mediaPlayer succeed");
        }
        //stopSelf(); //这句话多余了 费了好大劲
        // 音乐播放完毕的事件处理
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
        Log.d("myService", "onDestroy: finish!");
        super.onDestroy();
    }
}