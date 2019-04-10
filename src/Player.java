// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Player.java

import java.awt.*;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;

public class Player extends Thread
{

    public Player()
    {
        jump = false;
        jumping = false;
        done = false;
        peak = false;
        falling = false;
        rising = false;
        movingRight = false;
        grounded = true;
        dead = false;
        keyPressed = false;
        width = 10;
        height = 10;
        dy = 0;
        speed = 0.0D;
        jumpHeight = 47;
        accel = 0.0D;
        accel_increment = 0.025000000000000001D;
        accel_decrement = 0.016D;
        maxSpeed = 1.0D;
        run = 1.0D;
        walk = 0.34999999999999998D;
        deathHeight = 760;
        url = "player.png";
        ImageIcon ii = new ImageIcon(getClass().getResource("/images_10x10/player_2.png"));
        image = ii.getImage();
        groundHeight = y;
    }

    public void update()
    {
        if(dead)
            accel = 0.0D;
        move();
        if(y > groundHeight)
            grounded = false;
    }

    public void move()
    {
        if(y > deathHeight || y < 0)
        {
            dead = true;
            x = startX;
            y = startY;
        } else
        if(keyPressed)
        {
            if(accel < maxSpeed)
                accel += accel_increment;
            else
                accel = maxSpeed;
        } else
        if(accel > 0.0D)
            accel -= accel_decrement;
        else
            accel = 0.0D;
        x += accel * (double)dx;
        y += dy;
    }

    public void jump()
    {
        if(!jump)
        {
            grounded = false;
            jump = !jump;
            animator = new Thread(this);
            animator.start();
        }
    }

    public void draw(Graphics2D g2d)
    {
        g2d.drawImage(image, (int)Math.round(x), y, null);
    }

    public Image getImage()
    {
        return image;
    }

    public void setWalk()
    {
        maxSpeed = walk;
    }

    public void setRun()
    {
        maxSpeed = run;
    }

    public void setDeathHeight(int height)
    {
        deathHeight = height + 72;
    }

    public double getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public double getSpeed()
    {
        DecimalFormat twoDForm = new DecimalFormat("#.###");
        return Double.valueOf(twoDForm.format(speed)).doubleValue();
    }

    public void setStartPoint(int x, int y)
    {
        startX = x;
        startY = y;
    }

    public void reset()
    {
        x = startX;
        y = startY;
        dx = 0;
        grounded = true;
    }

    public void restart()
    {
        x = startX;
        y = startY;
        dx = 0;
        dead = !dead;
        grounded = true;
    }

    public int getStartX()
    {
        return startX;
    }

    public int getStartY()
    {
        return startY;
    }

    public void setDx(int dx)
    {
        lastDx = this.dx;
        this.dx = dx;
    }

    public int getDx()
    {
        return dx;
    }

    public int getLastDx()
    {
        return lastDx;
    }

    public void shoveLeft(double amnt)
    {
        x -= amnt;
    }

    public void shoveRight(double amnt)
    {
        x += amnt;
    }

    public void shoveDown(int amnt)
    {
        y += amnt;
    }

    public void reverseDx()
    {
        dx *= -1;
    }

    public void stopJump()
    {
        done = true;
    }

    public void setDy(int dy)
    {
        this.dy = dy;
    }

    public int getDy()
    {
        return dy;
    }

    public void correctY(int y)
    {
        this.y = y - height;
    }

    public int getCenterX()
    {
        return width / 2;
    }

    public int getCenterY()
    {
        return height / 2;
    }

    public void setGroundHeight(int height)
    {
        groundHeight = height;
    }

    public boolean isRising()
    {
        return rising;
    }

    public boolean isFalling()
    {
        return falling;
    }

    public boolean isMovingRight()
    {
        return movingRight;
    }

    public void movingRight(boolean right)
    {
        movingRight = right;
    }

    public void setPeak(boolean peaked)
    {
        peak = peaked;
    }

    public boolean isJumping()
    {
        return jump;
    }

    public Rectangle getBounds()
    {
        return new Rectangle((int)Math.round(x), y, width, height);
    }

    public void playSound(String s)
    {
    }

    public void run()
    {
        for(long beforeTime = System.currentTimeMillis(); !done && !dead; beforeTime = System.currentTimeMillis())
        {
            cycle();
            long timeDiff = System.currentTimeMillis() - beforeTime;
            long sleep = 8L - timeDiff;
            if(sleep < 0L)
                sleep = 2L;
            try
            {
                Thread.sleep(sleep);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        done = false;
        jump = false;
        peak = false;
    }

    public void cycle()
    {
        if(!peak)
        {
            rising = true;
            y -= 3;
        }
        if(y <= groundHeight - jumpHeight)
        {
            peak = true;
            rising = false;
        }
        if(peak && y + height <= groundHeight)
        {
            rising = false;
            setDy(1);
        }
        if(y + height == groundHeight && !falling)
            done = true;
        else
        if(y + height == groundHeight && falling)
        {
            groundHeight = 760;
            done = true;
        }
    }

    private Image image;
    private Thread animator;
    private boolean jump;
    private boolean jumping;
    private boolean done;
    private boolean peak;
    private boolean falling;
    private boolean rising;
    private boolean movingRight;
    public boolean grounded;
    public boolean dead;
    public boolean keyPressed;
    public int width;
    public int height;
    private double x;
    private int y;
    private int startX;
    private int startY;
    private int dx;
    private int lastDx;
    private int dy;
    private double speed;
    private int jumpHeight;
    private int groundHeight;
    private double accel;
    private double accel_increment;
    private double accel_decrement;
    private double maxSpeed;
    private double run;
    private double walk;
    private int deathHeight;
    String url;
}
