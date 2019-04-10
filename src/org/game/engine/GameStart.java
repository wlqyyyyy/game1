// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GameStart.java

package org.game.engine;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

// Referenced classes of package org.game.engine:
//            Game, GameLoop, GameCanvas

public class GameStart
{

    public GameStart()
    {
    }

    public static void start(final Game game, final GameLoop loop)
    {
        SwingUtilities.invokeLater(new Runnable() {

            public void run()
            {
                JFrame frame = new JFrame(game.getTitle());
                System.setProperty("sun.java2d.translaccel", "true");
                System.setProperty("sun.java2d.ddforcevram", "true");
                frame.setSize(game.getWidth(), game.getHeight());
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(3);
                GameCanvas canvas = new GameCanvas(game);
                frame.add(canvas);
                frame.setVisible(true);
                canvas.requestFocus();
                loop.setGame(game);
                loop.setCanvas(canvas);
                loop.start();
            }

        
        }
);
    }
}
