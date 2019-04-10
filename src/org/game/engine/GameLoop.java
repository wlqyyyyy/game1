// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GameLoop.java

package org.game.engine;

import java.util.logging.Level;
import java.util.logging.Logger;

// Referenced classes of package org.game.engine:
//            Game, GameCanvas

public class GameLoop extends Thread
{

    public GameLoop()
    {
        stopped = false;
        paused = false;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }

    public void setCanvas(GameCanvas canvas)
    {
        this.canvas = canvas;
    }

    public void pauseGame()
    {
        paused = true;
    }

    public boolean isPaused()
    {
        return paused;
    }

    public void resumeGame()
    {
        paused = false;
    }

    public void stopGame()
    {
        stopped = true;
    }

    public void run()
    {
        game.init();
        while(!game.isOver()) 
            if(!game.isOver() && !stopped)
            {
                beforeTime = System.currentTimeMillis();
                if(!paused)
                {
                    game.update();
                    canvas.repaint();
                }
                try
                {
                    Thread.sleep(game.getDelay());
                }
                catch(InterruptedException ex)
                {
                    Logger.getLogger(GameLoop.class.getName()).log(Level.SEVERE, null, ex);
                }
                currTime = System.currentTimeMillis();
            }
    }

    private Game game;
    private GameCanvas canvas;
    private boolean stopped;
    private boolean paused;
    private float time;
    private float beforeTime;
    private float currTime;
}
