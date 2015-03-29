package edu.rit.srtparser.srt;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by stefan987 on 3/26/2015.
 */
public class SRTView extends TextView {

    public SRTView(Context context) {
        super(context);
    }

    public SRTView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SRTView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void play(){
        //Timer ticking. For every tick, update the text view.

    }

    private void startTimer(){
        final Handler handler = new Handler();
        Timer timer = new Timer(false);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // Do whatever you want

                    }
                });
            }
        };
        timer.schedule(timerTask, 1000); // 1000 = 1 second.
    }

    public void playAt(Date position){

    }
}
