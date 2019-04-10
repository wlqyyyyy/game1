// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Game.java

package org.game.engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.*;

public abstract class Game
    implements KeyListener, MouseListener, MouseMotionListener
{

    public Game()
    {
        delay = 5;
        width = 400;
        height = 300;
        background = Color.black;
        title = "My Game";
    }

    public String getTitle()
    {
        return title;
    }

    public long getDelay()
    {
        return (long)delay;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public void resize(int i, int j)
    {
    }

    public Color getBackground()
    {
        return background;
    }

    public abstract void init();

    public abstract void update();

    public abstract void draw(Graphics2D graphics2d);

    public boolean isOver()
    {
        return over;
    }

    public void mouseDragged(MouseEvent mouseevent)
    {
    }

    public void mouseMoved(MouseEvent mouseevent)
    {
    }

    public void mouseClicked(MouseEvent mouseevent)
    {
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
    }

    public void mouseExited(MouseEvent mouseevent)
    {
    }

    public void mousePressed(MouseEvent mouseevent)
    {
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
    }

    public void keyPressed(KeyEvent keyevent)
    {
    }

    public void keyReleased(KeyEvent keyevent)
    {
    }

    public void keyTyped(KeyEvent keyevent)
    {
    }

    protected boolean over;
    protected int delay;
    protected int width;
    protected int height;
    protected Color background;
    protected String title;
}
