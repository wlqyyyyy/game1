// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Enemy.java

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Enemy
{

    public Enemy(int x, int y, boolean horizontal)
    {
        width = 12;
        height = 12;
        speed = 0.5D;
        this.horizontal = horizontal;
        ImageIcon ii;
        if(horizontal)
        {
            dx = 1;
            dy = 0;
            ii = new ImageIcon(getClass().getResource("images_12x12/zombie.png"));
        } else
        {
            dx = 0;
            dy = 1;
            ii = new ImageIcon(getClass().getResource("images_12x12/enemy_vert.png"));
        }
        image = ii.getImage();
        this.x = x;
        this.y = y;
    }

    public void move()
    {
        x += speed * (double)dx;
        y += speed * (double)dy;
    }

    public void reverseDirection()
    {
        if(horizontal)
        {
            if(dx == 1)
                x--;
            else
                x++;
        } else
        if(dy == 1)
            y--;
        else
            y++;
        dx *= -1;
        dy *= -1;
    }

    public boolean isHorizontal()
    {
        return horizontal;
    }

    public Image getImage()
    {
        return image;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public Rectangle getBounds()
    {
        return new Rectangle((int)Math.round(x), (int)Math.round(y), width, height);
    }

    private Image image;
    private int width;
    private int height;
    private double x;
    private double y;
    private int dx;
    private int dy;
    private double speed;
    private boolean horizontal;
}
