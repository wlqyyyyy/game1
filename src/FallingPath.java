// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FallingPath.java

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class FallingPath
{

    public FallingPath(int x, int y)
    {
        width = 12;
        height = 12;
        dx = 0;
        dy = 0;
        speed = 1;
        fallen = false;
        ImageIcon ii = new ImageIcon(getClass().getResource("images_12x12/path_falling.png"));
        image = ii.getImage();
        this.x = x;
        this.y = y;
    }

    public void move()
    {
        y += speed * dy;
        x += speed * dx;
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

    public void setDy(int dy)
    {
        this.dy = dy;
    }

    public void setFallen(boolean fallen)
    {
        this.fallen = fallen;
    }

    public boolean hasFallen()
    {
        return fallen;
    }

    public void correctY(int y)
    {
        this.y = y - height;
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
    private int dx;
    private int dy;
    private int speed;
    private boolean fallen;
}
