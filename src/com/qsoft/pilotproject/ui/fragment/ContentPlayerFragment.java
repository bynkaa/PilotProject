package com.qsoft.pilotproject.ui.fragment;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.PilotProject.R;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.pilotproject.utils.Utilities;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * User: BinkaA
 * Date: 10/17/13
 * Time: 11:24 PM
 */
@EFragment(R.layout.program_content_player)
public class ContentPlayerFragment extends Fragment
{

    public static final String MEDIA_PATH = "/sdcard/";


    private MediaPlayer mediaPlayer;
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


    private Runnable updateTimeTask = new Runnable()
    {
        @Override
        public void run()
        {
            if (mediaPlayer != null)
            {
                long totalDuration = mediaPlayer.getDuration();
                long currentDuration = mediaPlayer.getCurrentPosition();
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
        mediaPlayer = new MediaPlayer();
        playSong(getSong());
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
        int totalDuration = mediaPlayer.getDuration();
        int currentPosition = Utilities.progressToTimer(seekBar.getProgress(), totalDuration);
        mediaPlayer.seekTo(currentPosition);
        updateProgressBar();
    }

    @Click(R.id.ibPlayer)
    void doPlay()
    {
        if (mediaPlayer.isPlaying())
        {
            if (mediaPlayer != null)
            {
                mediaPlayer.pause();
                btPlay.setImageResource(R.drawable.content_button_play);
            }
        }
        else
        {
            if (mediaPlayer != null)
            {
                mediaPlayer.start();
                btPlay.setImageResource(R.drawable.content_button_pause);
            }
        }

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
        mediaPlayer.release();
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public void playSong(String path)
    {
        try
        {
            AssetFileDescriptor afd = getResources().openRawResourceFd(R.raw.music);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
            btPlay.setImageResource(R.drawable.content_button_pause);
            songProgressBar.setProgress(0);
            songProgressBar.setProgress(100);
            updateProgressBar();
        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void updateProgressBar()
    {
        handler.postDelayed(updateTimeTask, 100);

    }

    public String getSong()
    {
        File home = new File(MEDIA_PATH);
        for (File file : home.listFiles(new FileExtensionFilter()))
        {
            return file.getPath();
        }
        return "\\data\\a.mp3";
    }

    class FileExtensionFilter implements FilenameFilter
    {
        public boolean accept(File dir, String name)
        {
            return (name.endsWith(".mp3") || name.endsWith(".MP3"));
        }
    }


}