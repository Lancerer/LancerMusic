package com.example.lancer.lancermusic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PlayerReceiver extends BroadcastReceiver {
    private Context context;

    public PlayerReceiver(Context context) {
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
