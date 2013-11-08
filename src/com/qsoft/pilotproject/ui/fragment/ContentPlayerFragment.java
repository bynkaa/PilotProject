package com.qsoft.pilotproject.ui.fragment;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.PilotProject.R;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.pilotproject.ui.controller.MediaController;
import com.qsoft.pilotproject.utils.Utilities;

/**
 * User: BinkaA
 * Date: 10/17/13
 * Time: 11:24 PM
 */
@EFragment(R.layout.program_content_player)
public class ContentPlayerFragment extends Fragment
{
    @ViewById(R.id.seekBarPlayer)
    SeekBar songProgressBar;
    @ViewById(R.id.seekBarVolume)
    SeekBar volumeProgressBar;
    @ViewById(R.id.ibPlayer)
    ImageButton btPlay;
    @ViewById(R.id.tvTotalTime)
    TextView tvTotalDuration;
    @ViewById(R.id.tvTimeCurrent)
    TextView tvCurrentDuration;
    AudioManager audioManager = null;
    private Handler handler = new Handler();
    @Bean
    MediaController mediaController;


    private Runnable updateTimeTask = new Runnable()
    {
        @Override
        public void run()
        {
            if (mediaController.getMediaPlayer() != null)
            {
                long totalDuration = mediaController.getMediaPlayer().getDuration();
                long currentDuration = mediaController.getMediaPlayer().getCurrentPosition();
                tvTotalDuration.setText("" + Utilities.milliSecondsToTimer(totalDuration));
                tvCurrentDuration.setText("" + Utilities.milliSecondsToTimer(currentDuration));
                int progress = Utilities.getProgressPercentage(currentDuration, totalDuration);
                songProgressBar.setProgress(progress);
                handler.postDelayed(this, 100);
            }

        }
    };

    @AfterViews
    void afterViews()
    {
        mediaController.playSong(mediaController.getSong(), songProgressBar);
        updateProgressBar();
        this.getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
        initVolumeControls();
    }

    private void initVolumeControls()
    {
        audioManager = (AudioManager) this.getActivity().getSystemService(Context.AUDIO_SERVICE);
        volumeProgressBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        volumeProgressBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
    }

    @SeekBarTouchStart(R.id.seekBarPlayer)
    void onStartTrackingTouchPlayer(SeekBar seekBar)
    {
        handler.removeCallbacks(updateTimeTask);
    }

    @SeekBarTouchStop(R.id.seekBarPlayer)
    void onStopTrackingTouchPlayer(SeekBar seekBar)
    {
        handler.removeCallbacks(updateTimeTask);
        int totalDuration = mediaController.getMediaPlayer().getDuration();
        int currentPosition = Utilities.progressToTimer(seekBar.getProgress(), totalDuration);
        mediaController.getMediaPlayer().seekTo(currentPosition);
        updateProgressBar();
    }

    @SeekBarProgressChange(R.id.seekBarVolume)
    void onProgressChangeOnVolumnSeekBar(SeekBar seekBar, int progress, boolean b)
    {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
    }

    @Override
    public void onDestroy()
    {
        handler.removeCallbacks(updateTimeTask);
        mediaController.getMediaPlayer().release();
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }

    private void updateProgressBar()
    {
        handler.postDelayed(updateTimeTask, 100);
    }

}