package com.qsoft.pilotproject.ui.fragment;

import android.media.AudioManager;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.bindroid.ValueConverter;
import com.bindroid.ui.UiBinder;
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
    SeekBar songSeekBar;
    @ViewById(R.id.seekBarVolume)
    SeekBar volumeSeekBar;
    @ViewById(R.id.ibPlayer)
    ImageButton btPlay;
    @ViewById(R.id.tvTotalTime)
    TextView tvTotalDuration;
    @ViewById(R.id.tvTimeCurrent)
    TextView tvCurrentDuration;
    @SystemService
    AudioManager audioManager;
    private Handler handler = new Handler();
    @Bean
    MediaController mediaController;
    @Bean
    UpdateProgressBar updateProgressBar;

    @AfterViews
    void afterViews()
    {
        setRetainInstance(true);

        UiBinder.bind(getActivity(), R.id.ibPlayer, "imageResource", mediaController.getMediaPlayer(), "playing", new ValueConverter()
        {
            @Override
            public Object convertToSource(Object targetValue, Class<?> sourceType)
            {
                return super.convertToSource(targetValue, sourceType);    //To change body of overridden methods use File | Settings | File Templates.
            }

            @Override
            public Object convertToTarget(Object sourceValue, Class<?> targetType)
            {
                return super.convertToTarget(sourceValue, targetType);    //To change body of overridden methods use File | Settings | File Templates.
            }
        });
        mediaController.setActivity(this.getActivity());
        mediaController.setBtPlay(btPlay);
        mediaController.playSong();

        updateProgressBar.setHandler(handler);
        updateProgressBar.setMediaPlayer(mediaController.getMediaPlayer());
        updateProgressBar.setSongProgressBar(songSeekBar);
        updateProgressBar.setTvCurrentDuration(tvCurrentDuration);
        updateProgressBar.setTvTotalDuration(tvTotalDuration);
        updateProgressBar();

        initVolumeControls();
    }

    private void initVolumeControls()
    {
        volumeSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        volumeSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
    }

    @Click(R.id.ibPlayer)
    void doPlay()
    {
        if (mediaController.getMediaPlayer().isPlaying())
        {
            mediaController.getMediaPlayer().pause();
        }
        else
        {
            mediaController.getMediaPlayer().start();
        }
        mediaController.updateButtonImage();
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