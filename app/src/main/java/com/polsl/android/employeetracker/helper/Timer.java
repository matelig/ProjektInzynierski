package com.polsl.android.employeetracker.helper;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

/**
 * Created by m_lig on 02.11.2017.
 */

public class Timer {

    private final int MSG_START_TIMER = 0;
    private final int MSG_STOP_TIMER = 1;
    private final int MSG_UPDATE_TIMER = 2;

    private Stopwatch stopwatch = new Stopwatch();

    private Context context;

    final int REFRESH_RATE = 6000; //60 seconds

    public Timer(Context context) {
        this.context = context;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_START_TIMER:
                    stopwatch.start();
                    mHandler.sendEmptyMessage(MSG_UPDATE_TIMER);
                    break;

                case MSG_UPDATE_TIMER:
                    sendBroadcast();
                    mHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIMER,REFRESH_RATE);
                    break;

                case MSG_STOP_TIMER:
                    mHandler.removeMessages(MSG_UPDATE_TIMER);
                    stopwatch.stop();
                    sendBroadcast();
            }
        }
    };

    public void startTimer() {
        mHandler.sendEmptyMessage(MSG_START_TIMER);
    }

    public void stopTimer() {
        mHandler.sendEmptyMessage(MSG_STOP_TIMER);
    }

    private void sendBroadcast() {
        long time = stopwatch.getElapsedTimeMins();
        long hours = time / 60;
        long minutes = time % 60;
        Intent intent  = new Intent("elapsedTime");
        intent.putExtra("hour",hours);
        intent.putExtra("minute",minutes);
        context.sendBroadcast(intent);
    }
}
