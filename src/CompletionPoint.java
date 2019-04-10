// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CompletionPoint.java

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class CompletionPoint
{

    public CompletionPoint(int x, int y)
    {
        width = 12;
        height = 12;
        ImageIcon ii = new ImageIcon(getClass().getResource("images_12x12/completion_point_active.png"));
        imageActive = ii.getImage();
        ImageIcon ii2 = new ImageIcon(getClass().getResource("images_12x12/completion_point_inactive.png"));
        imageInactive = ii2.getImage();
        image = imageInactive;
        this.x = x;
        this.y = y;
    }

    public Image getImage()
    {
        return image;
    }

    public void setActive()
    {
        image = imageActive;
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
    private Image imageActive;
    private Image imageInactive;
    private int width;
    private int height;
    private int x;
    private int y;
}
