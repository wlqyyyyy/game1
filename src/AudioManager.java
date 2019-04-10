// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AudioManager.java

import java.applet.Applet;
import java.applet.AudioClip;

public class AudioManager extends Thread
{

    public AudioManager()
    {
        clip = new AudioClip[20];
        story = new AudioClip[20];
        background = new AudioClip[10];
        backMusicPlaying = true;
        bridgePlaying = false;
        elevatorPlaying = false;
        running = false;
        java.net.URL url = getClass().getResource("/sounds/star.wav");
        clip[0] = Applet.newAudioClip(url);
        url = getClass().getResource("/sounds/jump.wav");
        clip[1] = Applet.newAudioClip(url);
        url = getClass().getResource("/sounds/crash.wav");
        clip[2] = Applet.newAudioClip(url);
        url = getClass().getResource("/sounds/bridge.wav");
        clip[3] = Applet.newAudioClip(url);
        url = getClass().getResource("/sounds/bonk.wav");
        clip[4] = Applet.newAudioClip(url);
        url = getClass().getResource("/sounds/next_level.wav");
        clip[5] = Applet.newAudioClip(url);
        url = getClass().getResource("/sounds/pwned.wav");
        clip[6] = Applet.newAudioClip(url);
        url = getClass().getResource("/sounds/elevator.wav");
        clip[7] = Applet.newAudioClip(url);
        url = getClass().getResource("/sounds/evil_laugh.wav");
        clip[8] = Applet.newAudioClip(url);
        url = getClass().getResource("/sounds/menu_changed.wav");
        clip[9] = Applet.newAudioClip(url);
        url = getClass().getResource("/sounds/menu_entered.wav");
        clip[10] = Applet.newAudioClip(url);
        url = getClass().getResource("/sounds/menu_exited.wav");
        clip[11] = Applet.newAudioClip(url);
        url = getClass().getResource("/sounds/welcome.wav");
        clip[12] = Applet.newAudioClip(url);
        url = getClass().getResource("/sounds/eary_background.wav");
        background[0] = Applet.newAudioClip(url);
        url = getClass().getResource("/story/keys.wav");
        story[0] = Applet.newAudioClip(url);
    }

    public void startThread()
    {
        if(!running)
        {
            audioThread = new Thread(this);
            audioThread.start();
        }
        running = true;
    }

    public void run()
    {
        for(; running; running = false)
        {
            if(getTrack() == "star")
                try
                {
                    clip[0].play();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            else
            if(getTrack() == "jump")
                try
                {
                    clip[1].play();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            else
            if(getTrack() == "crash")
                try
                {
                    clip[2].play();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            else
            if(getTrack() == "bonk")
                try
                {
                    clip[4].play();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            else
            if(getTrack() == "next_level")
                try
                {
                    clip[5].play();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            else
            if(getTrack() == "pwned")
                try
                {
                    clip[6].play();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            else
            if(getTrack() == "evil_laugh")
                try
                {
                    clip[8].play();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            else
            if(getTrack() == "menu_changed")
                try
                {
                    clip[9].play();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            else
            if(getTrack() == "menu_entered")
                try
                {
                    clip[10].play();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            else
            if(getTrack() == "menu_exited")
                try
                {
                    clip[11].play();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            else
            if(getTrack() == "welcome")
                try
                {
                    clip[12].play();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            if(getStoryTrack() == "keys")
                try
                {
                    story[0].play();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            clearTracks();
        }

    }

    private void clearTracks()
    {
        track = "";
        storyTrack = "";
    }

    public void startBackgroundMusic(int index)
    {
        this.index = index;
        background[index].loop();
    }

    public String getTrack()
    {
        return track;
    }

    public String getStoryTrack()
    {
        return storyTrack;
    }

    public void playTrack(String soundTitle)
    {
        track = soundTitle;
        startThread();
    }

    public void playStoryTrack(String storyTitle)
    {
        storyTrack = storyTitle;
        startThread();
    }

    public void startBridge()
    {
        try
        {
            bridgePlaying = true;
            clip[3].loop();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void stopBridge()
    {
        try
        {
            bridgePlaying = false;
            clip[3].stop();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void startElevator()
    {
        try
        {
            elevatorPlaying = true;
            clip[7].loop();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void stopElevator()
    {
        try
        {
            elevatorPlaying = false;
            clip[7].stop();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean isPlayingBridge()
    {
        return bridgePlaying;
    }

    public boolean isPlayingElevator()
    {
        return elevatorPlaying;
    }

    public void setBackgroundMusic()
    {
        if(backMusicPlaying)
            background[index].stop();
        else
            background[index].loop();
        backMusicPlaying = !backMusicPlaying;
    }

    AudioClip clip[];
    AudioClip story[];
    AudioClip background[];
    private int index;
    private boolean backMusicPlaying;
    private boolean bridgePlaying;
    private boolean elevatorPlaying;
    private boolean running;
    Thread audioThread;
    private String track;
    private String storyTrack;
}
