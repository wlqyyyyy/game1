// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Elevator.java

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Elevator
{

    public Elevator(int x, int y, boolean large, boolean continuous, int tdy)
    {
        width = 24;
        height = 7;
        dx = 0;
        dy = 0;
        speed = 0.29999999999999999D;
        risen = false;
        eam = new AudioManager();
        ImageIcon ii = null;
        ImageIcon ii2 = null;
        if(large)
        {
            ii = new ImageIcon(getClass().getResource("images_24x7/elevator1.png"));
            ii2 = new ImageIcon(getClass().getResource("images_24x7/elevator2.png"));
            imageChanged = ii2.getImage();
        } else
        {
            ii = new ImageIcon(getClass().getResource("images_12x12/elevator2.png"));
        }
        if(continuous)
            dy = tdy;
        imageNormal = ii.getImage();
        this.x = x;
        this.y = y;
        startY = y;
        this.tdy = tdy;
        this.large = large;
        this.continuous = continuous;
    }

    public void move()
    {
        if((double)Math.round(y) != startY)
        {
            if(large)
                image = imageChanged;
        } else
        if(large)
            image = imageNormal;
        y += speed * (double)dy;
        if((double)Math.round(getY()) == getStartY() && hasRisen())
        {
            setRisen(false);
            if(!isContinuous())
            {
                stopAudio();
                setDy(0);
            } else
            {
                setDy(getTdy());
            }
        }
    }

    public void startAudio()
    {
        if(!eam.isPlayingElevator() && !isContinuous())
            eam.startElevator();
    }

    public void stopAudio()
    {
        if(eam.isPlayingElevator())
            eam.stopElevator();
    }

    public Image getImage()
    {
        return image;
    }

    public void start()
    {
        dy = tdy;
    }

    public void setOppositeDy()
    {
        dy = tdy * -1;
    }

    public int getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public double getStartY()
    {
        return startY;
    }

    public void setDy(int dy)
    {
        this.dy = dy;
    }

    public int getTdy()
    {
        return tdy;
    }

    public void reverseDy()
    {
        dy *= -1;
    }

    public void setRisen(boolean risen)
    {
        this.risen = risen;
    }

    public boolean hasRisen()
    {
        return risen;
    }

    public boolean isContinuous()
    {
        return continuous;
    }

    public void correctY(int y)
    {
        this.y = y - height;
    }

    public Rectangle getBounds()
    {
        return new Rectangle(x, (int)Math.round(y), width, height);
    }

    private Image image;
    private Image imageChanged;
    private Image imageNormal;
    private int width;
    private int height;
    private int x;
    private double y;
    private double startY;
    private int dx;
    private int dy;
    private int tdy;
    private double speed;
    private boolean large;
    private boolean continuous;
    private boolean risen;
    AudioManager eam;
}
