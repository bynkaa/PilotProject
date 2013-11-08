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
import com.qsoft.pilotproject.ui.fragment.player.UpdateProgressBar;
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
    @Bean
    UpdateProgressBar updateProgressBar;

    @AfterViews
    void afterViews()
    {
        setRetainInstance(true);
        mediaController.playSong(mediaController.getSong(), songProgressBar);

        updateProgressBar.setHandler(handler);
        updateProgressBar.setMediaPlayer(mediaController.getMediaPlayer());
        updateProgressBar.setSongProgressBar(songProgressBar);
        updateProgressBar.setTvCurrentDuration(tvCurrentDuration);
        updateProgressBar.setTvTotalDuration(tvTotalDuration);
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
        handler.removeCallbacks(updateProgressBar);
    }

    @SeekBarTouchStop(R.id.seekBarPlayer)
    void onStopTrackingTouchPlayer(SeekBar seekBar)
    {
        handler.removeCallbacks(updateProgressBar);
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
        handler.removeCallbacks(updateProgressBar);
        if (mediaController.getMediaPlayer() != null)
        {
            mediaController.getMediaPlayer().release();
        }
        super.onDestroy();
    }

    private void updateProgressBar()
    {
        handler.postDelayed(updateProgressBar, 100);
    }

}