package com.u21.a0903_onlinemusic.service;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.Timer;

public class Service extends android.app.Service {

    private MediaPlayer player;
    private Timer timer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
