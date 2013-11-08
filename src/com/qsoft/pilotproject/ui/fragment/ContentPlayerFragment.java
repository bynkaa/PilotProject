package com.qsoft.pilotproject.ui.fragment;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.PilotProject.R;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;
import com.qsoft.pilotproject.ui.fragment.player.UpdateProgressBar;
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
    SeekBar.OnSeekBarChangeListener seekBarSongListener = new SeekBar.OnSeekBarChangeListener()
    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b)
        {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        {
            handler.removeCallbacks(updateProgressBar);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {
            handler.removeCallbacks(updateProgressBar);
            int totalDuration = mediaPlayer.getDuration();
            int currentPosition = Utilities.progressToTimer(seekBar.getProgress(), totalDuration);
            mediaPlayer.seekTo(currentPosition);
            updateProgressBar();
        }

    };
    View.OnClickListener btPlayOnclickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
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
    };
    MediaPlayer mediaPlayer;
    @ViewById(R.id.seekBarPlayer)
    SeekBar songProgressBar;
    Handler handler = new Handler();
    @ViewById(R.id.seekBarVolume)
    SeekBar volumeProgressBar;
    @ViewById(R.id.ibPlayer)
    ImageButton btPlay;
    AudioManager audioManager = null;
    @ViewById(R.id.tvTotalTime)
    TextView tvTotalDuration;
    @ViewById(R.id.tvTimeCurrent)
    TextView tvCurrentDuration;

    @Bean
    UpdateProgressBar updateProgressBar;

    @AfterViews
    void afterViews()
    {
        setRetainInstance(true);
        btPlay.setOnClickListener(btPlayOnclickListener);
        songProgressBar.setOnSeekBarChangeListener(seekBarSongListener);
        playSong(getSong());
        this.getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
        initVolumeControls();

        updateProgressBar.setHandler(handler);
        updateProgressBar.setMediaPlayer(mediaPlayer);
        updateProgressBar.setSongProgressBar(songProgressBar);
        updateProgressBar.setTvCurrentDuration(tvCurrentDuration);
        updateProgressBar.setTvTotalDuration(tvTotalDuration);
    }

    private void initVolumeControls()
    {
        audioManager = (AudioManager) this.getActivity().getSystemService(Context.AUDIO_SERVICE);
        volumeProgressBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        volumeProgressBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

        volumeProgressBar.setOnSeekBarChangeListener(volumeProgressBarOnChangeListener);
    }

    SeekBar.OnSeekBarChangeListener volumeProgressBarOnChangeListener = new SeekBar.OnSeekBarChangeListener()
    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean b)
        {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    };

    @Override
    public void onDestroy()
    {
        handler.removeCallbacks(updateProgressBar);
        if (mediaPlayer != null)
        {
            mediaPlayer.release();
        }
        mediaPlayer = null;
        super.onDestroy();
    }

    public void playSong(String path)
    {
        try
        {
            if (mediaPlayer == null)
            {
                mediaPlayer = new MediaPlayer();
            }
            if (mediaPlayer.isPlaying())
            {
                return;
            }
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
        handler.postDelayed(updateProgressBar, 100);

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