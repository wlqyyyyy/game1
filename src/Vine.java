// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Vine.java

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Vine
{

    public Vine(int x, int y)
    {
        width = 12;
        height = 12;
        ImageIcon ii = new ImageIcon(getClass().getResource("images_12x12/vine.png"));
        image = ii.getImage();
        this.x = x;
        this.y = y;
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
}
