// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Bridge.java

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Bridge
{

    public Bridge(int x, int y, boolean large, boolean continuous, int tdx)
    {
        width = 24;
        height = 7;
        dx = 0;
        dy = 0;
        speed = 0.20000000000000001D;
        moved = false;
        bam = new AudioManager();
        ImageIcon ii = null;
        ImageIcon ii2 = null;
        if(large)
        {
            ii = new ImageIcon(getClass().getResource("images_24x7/bridge1.png"));
            ii2 = new ImageIcon(getClass().getResource("images_24x7/bridge2.png"));
            imageChanged = ii2.getImage();
        } else
        {
            ii = new ImageIcon(getClass().getResource("images_12x12/elevator2.png"));
        }
        imageNormal = ii.getImage();
        if(continuous)
            dx = 1;
        this.tdx = tdx;
        this.x = x;
        this.y = y;
        startX = x;
        this.large = large;
        this.continuous = continuous;
    }

    public void move()
    {
        if(!continuous)
        {
            if((double)Math.round(x) != startX)
            {
                if(large)
                    image = imageChanged;
            } else
            if(large)
                image = imageNormal;
        } else
        {
            image = imageChanged;
        }
        x += speed * (double)dx;
        if((double)Math.round(getX()) == getStartX() && hasMoved())
        {
            setMoved(false);
            if(!isContinuous())
            {
                stopAudio();
                setDx(0);
            }
        }
    }

    public void startAudio()
    {
        if(!bam.isPlayingBridge() && !isContinuous())
            bam.startBridge();
    }

    public void stopAudio()
    {
        if(bam.isPlayingBridge())
            bam.stopBridge();
    }

    public Image getImage()
    {
        return image;
    }

    public void start()
    {
        dx = tdx;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getStartY()
    {
        return startY;
    }

    public void setDy(int dy)
    {
        this.dy = dy;
    }

    public void setDx(int dx)
    {
        this.dx = dx;
    }

    public int getDx()
    {
        return dx;
    }

    public void shoveLeft(double amnt)
    {
        x -= amnt;
    }

    public void shoveRight(double amnt)
    {
        x += amnt;
    }

    public void reverseDx()
    {
        dx *= -1;
    }

    public double getStartX()
    {
        return startX;
    }

    public void setMoved(boolean moved)
    {
        this.moved = moved;
    }

    public boolean hasMoved()
    {
        return moved;
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
        return new Rectangle((int)Math.round(x), (int)Math.round(y), width, height);
    }

    private Image image;
    private Image imageChanged;
    private Image imageNormal;
    private int width;
    private int height;
    private double x;
    private double y;
    private double startY;
    private double startX;
    private int dx;
    private int tdx;
    private int dy;
    private double speed;
    private boolean large;
    private boolean continuous;
    private boolean moved;
    private AudioManager bam;
}
