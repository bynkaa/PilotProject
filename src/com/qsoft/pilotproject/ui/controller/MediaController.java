package com.qsoft.pilotproject.ui.controller;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageButton;
import android.widget.SeekBar;
import com.example.PilotProject.R;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.api.Scope;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * User: binhtv
 * Date: 11/8/13
 * Time: 9:19 AM
 */
@EBean
public class MediaController
{
    public static final String MEDIA_PATH = "/sdcard/";
    @ViewById(R.id.ibPlayer)
    ImageButton btPlay;
    @RootContext
    Activity activity;

    private MediaPlayer mediaPlayer;

    public MediaController()
    {
        mediaPlayer = new MediaPlayer();
    }

    public MediaPlayer getMediaPlayer()
    {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer)
    {
        this.mediaPlayer = mediaPlayer;
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

    public void playSong(String path, SeekBar songProgressBar)
    {
        try
        {
            if (mediaPlayer == null)
                mediaPlayer = new MediaPlayer();
            if (mediaPlayer.isPlaying())
                return;
            AssetFileDescriptor afd = activity.getResources().openRawResourceFd(R.raw.music);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
            btPlay.setImageResource(R.drawable.content_button_pause);
            songProgressBar.setProgress(0);
            songProgressBar.setProgress(100);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
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
