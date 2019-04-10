// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Spike.java

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Spike
{

    public Spike(int x, int y, boolean upward)
    {
        width = 12;
        height = 12;
        facingUp = upward;
        ImageIcon ii;
        if(upward)
            ii = new ImageIcon(getClass().getResource("images_12x12/spike_up_2.png"));
        else
            ii = new ImageIcon(getClass().getResource("images_12x12/spike_down_2.png"));
        image = ii.getImage();
        this.x = x;
        this.y = y;
    }

    public boolean isUpwardFacing()
    {
        return facingUp;
    }

    public Image getImage()
    {
        return image;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public Rectangle getBounds()
    {
        return new Rectangle(x, y, width, height);
    }

    private Image image;
    private int width;
    private int height;
    private int x;
    private int y;
    private boolean facingUp;
}
