// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GameCanvas.java

package org.game.engine;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JComponent;

// Referenced classes of package org.game.engine:
//            Game

public class GameCanvas extends JComponent
    implements ComponentListener
{

    public GameCanvas(Game game)
    {
        this.game = game;
        addKeyListener(this.game);
        addMouseListener(this.game);
        addMouseMotionListener(this.game);
        setDoubleBuffered(true);
        requestFocus();
        addComponentListener(this);
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(game.background);
        g2d.fillRect(0, 0, game.width, game.height);
        game.draw(g2d);
    }

    public void componentHidden(ComponentEvent componentevent)
    {
    }

    public void componentMoved(ComponentEvent componentevent)
    {
    }

    public void componentResized(ComponentEvent ce)
    {
        game.resize(ce.getComponent().getWidth(), ce.getComponent().getHeight());
    }

    public void componentShown(ComponentEvent componentevent)
    {
    }

    private final Game game;
}
