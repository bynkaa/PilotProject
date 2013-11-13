package com.qsoft.pilotproject.ui.controller;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.api.Scope;
import com.qsoft.pilotproject.R;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * User: binhtv
 * Date: 11/8/13
 * Time: 9:19 AM
 */
@EBean(scope = Scope.Singleton)
public class MediaController
{
    public static final String MEDIA_PATH = "/sdcard/";
    private Activity activity;

    private MediaPlayer mediaPlayer;

    public MediaController()
    {
        mediaPlayer = new MediaPlayer();
    }

    public void playSong()
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
            if (!mediaPlayer.isPlaying() && mediaPlayer.getCurrentPosition() > 0)
            {
                return;
            }
            AssetFileDescriptor afd = getActivity().getResources().openRawResourceFd(R.raw.music);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
//        finally
//        {
//            updateButtonImage();
//        }
    }


    public Activity getActivity()
    {
        return activity;
    }

    public void setActivity(Activity activity)
    {
        this.activity = activity;
    }

    public MediaPlayer getMediaPlayer()
    {
        return mediaPlayer;
    }

    class FileExtensionFilter implements FilenameFilter
    {
        public boolean accept(File dir, String name)
        {
            return (name.endsWith(".mp3") || name.endsWith(".MP3"));
        }
    }


}
