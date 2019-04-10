// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Path.java

import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;
import javax.swing.ImageIcon;

public class Path
{

    public Path(int x, int y)
    {
        width = 12;
        height = 12;
        r = new Random();
        int ran = r.nextInt(11);
        if(ran >= 5)
            ran = 5;
        ImageIcon ii = new ImageIcon(getClass().getResource((new StringBuilder("images_12x12/path_safe_")).append(ran).append(".png").toString()));
        image = ii.getImage();
        imageNormal = image;
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

    public int getCenterX()
    {
        return width / 2;
    }

    public int getY()
    {
        return y;
    }

    public void setChanged()
    {
        image = imageChanged;
    }

    public void setNormal()
    {
        image = imageNormal;
    }

    public Rectangle getBounds()
    {
        return new Rectangle(x, y, width, height);
    }

    private Image image;
    private Image imageNormal;
    private Image imageChanged;
    private int width;
    private int height;
    private int x;
    private int y;
    Random r;
}
