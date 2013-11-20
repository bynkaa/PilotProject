package com.qsoft.pilotproject.ui.fragment.player;

import android.media.MediaPlayer;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.api.Scope;
import com.qsoft.pilotproject.common.utils.Utilities;

/**
 * User: Le
 * Date: 11/8/13
 */
@EBean(scope = Scope.Singleton)
public class UpdateProgressBar implements Runnable {
// ------------------------------ FIELDS ------------------------------

    private MediaPlayer mediaPlayer;
    private TextView tvTotalDuration;
    private TextView tvCurrentDuration;

    private ProgressBar songProgressBar;
    private Handler handler;

// --------------------- GETTER / SETTER METHODS ---------------------

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public ProgressBar getSongProgressBar() {
        return songProgressBar;
    }

    public void setSongProgressBar(ProgressBar songProgressBar) {
        this.songProgressBar = songProgressBar;
    }

    public TextView getTvCurrentDuration() {
        return tvCurrentDuration;
    }

    public void setTvCurrentDuration(TextView tvCurrentDuration) {
        this.tvCurrentDuration = tvCurrentDuration;
    }

    public TextView getTvTotalDuration() {
        return tvTotalDuration;
    }

    public void setTvTotalDuration(TextView tvTotalDuration) {
        this.tvTotalDuration = tvTotalDuration;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Runnable ---------------------

    @Override
    public void run() {
        if (mediaPlayer != null) {
            long totalDuration = mediaPlayer.getDuration();
            long currentDuration = mediaPlayer.getCurrentPosition();
            tvTotalDuration.setText("" + Utilities.milliSecondsToTimer(totalDuration));
            tvCurrentDuration.setText("" + Utilities.milliSecondsToTimer(currentDuration));
            int progress = Utilities.getProgressPercentage(currentDuration, totalDuration);
            songProgressBar.setProgress(progress);
            handler.postDelayed(this, 100);
        }
    }
}
