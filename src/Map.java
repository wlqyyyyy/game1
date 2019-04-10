// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Map.java

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.PrintStream;
import java.util.ArrayList;

public class Map extends Thread
{

    public Map()
    {
        paths = new ArrayList();
        fallingPaths = new ArrayList();
        vines = new ArrayList();
        elevators = new ArrayList();
        bridges = new ArrayList();
        spikes = new ArrayList();
        enemies = new ArrayList();
        completionPts = new ArrayList();
        loadingLevel = true;
        mam = new AudioManager();
    }

    public void draw(Graphics2D g2d)
    {
        for(int i = 0; i < paths.size(); i++)
        {
            Path mp = (Path)paths.get(i);
            g2d.drawImage(mp.getImage(), mp.getX(), mp.getY(), null);
        }

        for(int i = 0; i < fallingPaths.size(); i++)
        {
            FallingPath mp = (FallingPath)fallingPaths.get(i);
            g2d.drawImage(mp.getImage(), mp.getX(), mp.getY(), null);
        }

        for(int i = 0; i < elevators.size(); i++)
        {
            Elevator mp = (Elevator)elevators.get(i);
            g2d.drawImage(mp.getImage(), mp.getX(), (int)Math.round(mp.getY()), null);
        }

        for(int i = 0; i < bridges.size(); i++)
        {
            Bridge mp = (Bridge)bridges.get(i);
            g2d.drawImage(mp.getImage(), (int)Math.round(mp.getX()), (int)Math.round(mp.getY()), null);
        }

        for(int i = 0; i < spikes.size(); i++)
        {
            Spike mp = (Spike)spikes.get(i);
            g2d.drawImage(mp.getImage(), mp.getX(), mp.getY(), null);
        }

        for(int i = 0; i < enemies.size(); i++)
        {
            Enemy mp = (Enemy)enemies.get(i);
            g2d.drawImage(mp.getImage(), (int)Math.round(mp.getX()), (int)Math.round(mp.getY()), null);
        }

        for(int i = 0; i < vines.size(); i++)
        {
            Vine mp = (Vine)vines.get(i);
            g2d.drawImage(mp.getImage(), mp.getX(), mp.getY(), null);
        }

    }

    public void update(Player player)
    {
        if(!player.dead)
        {
            Rectangle playerRect = player.getBounds();
            for(int i = 0; i < getPathSize(); i++)
            {
                Path currPath = getPath(i);
                Rectangle currRect = currPath.getBounds();
                if(playerRect.intersects(currRect))
                {
                    if(player.getY() < currRect.y)
                    {
                        player.grounded = true;
                        player.setGroundHeight(getPath(i).getBounds().y + 1);
                        player.correctY(getPath(i).getBounds().y);
                        break;
                    }
                    if(player.getY() > currRect.y && player.isRising())
                    {
                        mam.playTrack("bonk");
                        player.setPeak(true);
                    }
                    if(player.getX() < (double)currRect.x)
                    {
                        player.shoveLeft(0.5D);
                        player.setDx(0);
                    } else
                    if(player.getX() > (double)currRect.x)
                    {
                        player.shoveRight(0.5D);
                        player.setDx(0);
                    }
                }
                player.setDy(1);
            }

            for(int i = 0; i < getSpikeSize(); i++)
            {
                Spike spike = getSpike(i);
                Rectangle spRect = spike.getBounds();
                if(!playerRect.intersects(spRect))
                    continue;
                boolean canDie = true;
                if(player.getY() < spRect.y && spike.isUpwardFacing() && !player.isJumping())
                {
                    player.reset();
                    player.dead = true;
                    player.grounded = true;
                    break;
                }
                if(player.getY() > spRect.y && player.getY() < spRect.y + 2 || (double)player.getY() > spRect.getCenterY() && spike.isUpwardFacing())
                {
                    if(player.isRising())
                    {
                        mam.playTrack("bonk");
                        player.setPeak(true);
                    }
                    canDie = false;
                }
                if(player.getY() < spRect.y && !spike.isUpwardFacing())
                {
                    player.grounded = true;
                    player.setGroundHeight(getSpike(i).getBounds().y + 1);
                    player.correctY(getSpike(i).getBounds().y);
                    canDie = false;
                } else
                if(player.getY() > spRect.y && !spike.isUpwardFacing())
                {
                    player.reset();
                    player.dead = true;
                    player.grounded = true;
                    break;
                }
                if(player.getX() < (double)spRect.x && canDie)
                {
                    player.reset();
                    player.dead = true;
                    player.grounded = true;
                    break;
                }
                if(player.getX() <= (double)spRect.x || !canDie)
                    continue;
                player.reset();
                player.dead = true;
                player.grounded = true;
                break;
            }

            for(int i = 0; i < fallingPaths.size(); i++)
            {
                Rectangle fp = ((FallingPath)fallingPaths.get(i)).getBounds();
                ((FallingPath)fallingPaths.get(i)).move();
                if(playerRect.intersects(fp))
                {
                    player.grounded = true;
                    if(!((FallingPath)fallingPaths.get(i)).hasFallen())
                        ((FallingPath)fallingPaths.get(i)).setDy(1);
                    else
                    if(player.getY() < fp.y)
                    {
                        player.grounded = true;
                        player.setGroundHeight(getFallingPath(i).getBounds().y + 1);
                        player.correctY(getFallingPath(i).getBounds().y);
                    }
                }
                if(getFallingPath(i).getY() > 760)
                    removeFallingPath(i);
                player.setDy(1);
            }

            for(int i = 0; i < elevators.size(); i++)
            {
                Rectangle fp = ((Elevator)elevators.get(i)).getBounds();
                ((Elevator)elevators.get(i)).move();
                if(getElevator(i).getY() > 760D)
                    removeElevator(i);
                if(playerRect.intersects(fp))
                {
                    player.grounded = true;
                    if(!((Elevator)elevators.get(i)).hasRisen() && playerRect.getCenterX() < fp.getCenterX() + 6D && playerRect.getCenterX() > fp.getCenterX() - 6D)
                    {
                        ((Elevator)elevators.get(i)).startAudio();
                        ((Elevator)elevators.get(i)).start();
                    }
                    if(player.getY() < fp.y)
                    {
                        player.grounded = true;
                        player.setGroundHeight(getElevator(i).getBounds().y + 1);
                        player.correctY(getElevator(i).getBounds().y);
                    } else
                    if(player.getY() > fp.y)
                    {
                        if(player.isRising())
                        {
                            mam.playTrack("bonk");
                            player.setPeak(true);
                        }
                    } else
                    if(player.getX() < (double)fp.x)
                    {
                        player.shoveLeft(1.0D);
                        player.setDx(0);
                    } else
                    if(player.getX() > (double)fp.x)
                    {
                        player.shoveRight(1.0D);
                        player.setDx(0);
                    }
                }
                player.setDy(1);
            }

            for(int i = 0; i < bridges.size(); i++)
            {
                Rectangle bp = ((Bridge)bridges.get(i)).getBounds();
                ((Bridge)bridges.get(i)).move();
                if(getBridge(i).getY() > 760D)
                    removeBridge(i);
                if(playerRect.intersects(bp))
                {
                    player.grounded = true;
                    if(!((Bridge)bridges.get(i)).hasMoved() && playerRect.getCenterX() < bp.getCenterX() + 3D && playerRect.getCenterX() > bp.getCenterX() - 3D)
                    {
                        ((Bridge)bridges.get(i)).startAudio();
                        ((Bridge)bridges.get(i)).start();
                    }
                    if(player.getY() < bp.y)
                    {
                        player.grounded = true;
                        player.setGroundHeight(getBridge(i).getBounds().y + 1);
                        player.correctY(getBridge(i).getBounds().y);
                    } else
                    if(player.getY() > bp.y)
                    {
                        if(player.isRising())
                        {
                            mam.playTrack("bonk");
                            player.setPeak(true);
                        }
                    } else
                    if(player.getX() < (double)bp.x)
                    {
                        player.shoveLeft(1.0D);
                        player.setDx(0);
                    } else
                    if(player.getX() > (double)bp.x)
                    {
                        player.shoveRight(1.0D);
                        player.setDx(0);
                    }
                }
                player.setDy(1);
            }

            for(int i = 0; i < getEnemySize(); i++)
            {
                Enemy enemy = getEnemy(i);
                enemy.move();
                Rectangle eRect = enemy.getBounds();
                if(!playerRect.intersects(eRect))
                    continue;
                player.dead = true;
                player.grounded = true;
                break;
            }

            for(int i = 0; i < vines.size(); i++)
            {
                Vine vine = (Vine)vines.get(i);
                Rectangle gRect = vine.getBounds();
                if(!playerRect.intersects(gRect))
                    continue;
                player.dead = true;
                break;
            }

        }
    }

    public void addPath(int x, int y)
    {
        paths.add(new Path(x, y));
    }

    public void removePath(int index)
    {
        paths.remove(index);
    }

    public Path getPath(int index)
    {
        return (Path)paths.get(index);
    }

    public int getPathSize()
    {
        return paths.size();
    }

    public void addSpike(int x, int y, boolean upwards)
    {
        spikes.add(new Spike(x, y, upwards));
    }

    public void removeSpike(int index)
    {
        spikes.remove(index);
    }

    public Spike getSpike(int index)
    {
        return (Spike)spikes.get(index);
    }

    public int getSpikeSize()
    {
        return spikes.size();
    }

    public void addEnemy(int x, int y, boolean horizontal)
    {
        enemies.add(new Enemy(x, y, horizontal));
    }

    public void removeEnemy(int index)
    {
        enemies.remove(index);
    }

    public Enemy getEnemy(int index)
    {
        return (Enemy)enemies.get(index);
    }

    public int getEnemySize()
    {
        return enemies.size();
    }

    public void addFallingPath(int x, int y)
    {
        fallingPaths.add(new FallingPath(x, y));
    }

    public void removeFallingPath(int index)
    {
        fallingPaths.remove(index);
    }

    public FallingPath getFallingPath(int index)
    {
        return (FallingPath)fallingPaths.get(index);
    }

    public int getFallingPathSize()
    {
        return fallingPaths.size();
    }

    public void addElevator(int x, int y, boolean large, boolean continuous, int dy)
    {
        elevators.add(new Elevator(x, y, large, continuous, dy));
    }

    public void removeElevator(int index)
    {
        elevators.remove(index);
    }

    public Elevator getElevator(int index)
    {
        return (Elevator)elevators.get(index);
    }

    public int getElevatorListSize()
    {
        return elevators.size();
    }

    public void addBridge(int x, int y, boolean large, boolean continuous, int dx)
    {
        bridges.add(new Bridge(x, y, large, continuous, dx));
    }

    public void removeBridge(int index)
    {
        bridges.remove(index);
    }

    public Bridge getBridge(int index)
    {
        return (Bridge)bridges.get(index);
    }

    public int getBridgeListSize()
    {
        return bridges.size();
    }

    public void addGas(int x, int y)
    {
        vines.add(new Vine(x, y));
    }

    public void clear()
    {
        paths.clear();
        fallingPaths.clear();
        spikes.clear();
        enemies.clear();
        for(int i = 0; i < elevators.size(); i++)
            ((Elevator)elevators.get(i)).stopAudio();

        elevators.clear();
        for(int i = 0; i < bridges.size(); i++)
            ((Bridge)bridges.get(i)).stopAudio();

        bridges.clear();
        vines.clear();
    }

    public void startThread()
    {
        mapThread = new Thread(this);
        mapThread.start();
    }

    public void run()
    {
        while(!loadingLevel) 
        {
            try
            {
                Thread.sleep(10L);
            }
            catch(InterruptedException e1)
            {
                e1.printStackTrace();
            }
            try
            {
                for(int i = 0; i < getPathSize(); i++)
                {
                    Path mp = (Path)paths.get(i);
                    Rectangle mpRect = mp.getBounds();
                    for(int j = 0; j < enemies.size(); j++)
                    {
                        Enemy enemy = (Enemy)enemies.get(j);
                        Rectangle eRect = enemy.getBounds();
                        if(eRect.intersects(mpRect))
                            enemy.reverseDirection();
                    }

                }

                for(int i = 0; i < getFallingPathSize(); i++)
                    try
                    {
                        FallingPath fallingPiece = (FallingPath)fallingPaths.get(i);
                        Rectangle fallingRect = fallingPiece.getBounds();
                        for(int j = 0; j < fallingPaths.size(); j++)
                        {
                            FallingPath mp = (FallingPath)fallingPaths.get(j);
                            Rectangle mpRect = mp.getBounds();
                            if(fallingPiece != mp && fallingRect.intersects(mpRect) && fallingRect.y < mpRect.y)
                            {
                                mam.playTrack("crash");
                                fallingPiece.setFallen(true);
                                fallingPiece.setDy(0);
                                fallingPiece.correctY(mp.getY());
                            }
                        }

                        for(int j = 0; j < getPathSize(); j++)
                        {
                            Path mp = (Path)paths.get(j);
                            Rectangle mpRect = mp.getBounds();
                            if(fallingRect.intersects(mpRect) && fallingRect.y < mpRect.y)
                            {
                                mam.playTrack("crash");
                                fallingPiece.setFallen(true);
                                fallingPiece.setDy(0);
                                fallingPiece.correctY(mp.getY());
                            }
                        }

                        for(int j = 0; j < getSpikeSize(); j++)
                        {
                            Spike mp = (Spike)spikes.get(j);
                            Rectangle mpRect = mp.getBounds();
                            if(fallingRect.intersects(mpRect) && fallingRect.getY() < (double)mpRect.y && !mp.isUpwardFacing())
                            {
                                mam.playTrack("crash");
                                fallingPiece.setFallen(true);
                                fallingPiece.setDy(0);
                                fallingPiece.correctY(mp.getY());
                                break;
                            }
                            if(!fallingRect.intersects(mpRect) || fallingRect.y >= mpRect.y)
                                continue;
                            spikes.remove(mp);
                            break;
                        }

                    }
                    catch(NullPointerException ex)
                    {
                        i++;
                    }

                for(int i = 0; i < elevators.size(); i++)
                    try
                    {
                        Elevator elevatorPiece = (Elevator)elevators.get(i);
                        Rectangle elevatorRect = elevatorPiece.getBounds();
                        for(int j = 0; j < elevators.size(); j++)
                        {
                            Elevator mp = (Elevator)elevators.get(j);
                            Rectangle mpRect = mp.getBounds();
                            if(elevatorPiece != mp && elevatorRect.intersects(mpRect))
                            {
                                ((Elevator)elevators.get(i)).setRisen(true);
                                elevatorPiece.setOppositeDy();
                            }
                        }

                        for(int j = 0; j < paths.size(); j++)
                        {
                            Path mp = (Path)paths.get(j);
                            Rectangle mpRect = mp.getBounds();
                            if(elevatorRect.intersects(mpRect))
                            {
                                ((Elevator)elevators.get(i)).setRisen(true);
                                elevatorPiece.setOppositeDy();
                            }
                        }

                        for(int j = 0; j < spikes.size(); j++)
                        {
                            Spike mp = (Spike)spikes.get(j);
                            Rectangle mpRect = mp.getBounds();
                            if(elevatorRect.intersects(mpRect))
                            {
                                ((Elevator)elevators.get(i)).setRisen(true);
                                elevatorPiece.setOppositeDy();
                            }
                        }

                    }
                    catch(NullPointerException ex)
                    {
                        i++;
                    }

                for(int i = 0; i < bridges.size(); i++)
                    try
                    {
                        Bridge bridgePiece = (Bridge)bridges.get(i);
                        Rectangle bridgeRect = bridgePiece.getBounds();
                        for(int j = 0; j < bridges.size(); j++)
                        {
                            Bridge mp = (Bridge)bridges.get(j);
                            Rectangle mpRect = mp.getBounds();
                            if(bridgePiece != mp && bridgeRect.intersects(mpRect))
                            {
                                ((Bridge)bridges.get(i)).setMoved(true);
                                if(bridgePiece.isContinuous())
                                    if(bridgePiece.getX() > (double)mpRect.x)
                                        bridgePiece.shoveRight(1.0D);
                                    else
                                    if(bridgePiece.getX() < (double)mpRect.x)
                                        bridgePiece.shoveLeft(1.0D);
                                bridgePiece.reverseDx();
                            }
                        }

                        for(int j = 0; j < paths.size(); j++)
                        {
                            Path mp = (Path)paths.get(j);
                            Rectangle mpRect = mp.getBounds();
                            if(bridgeRect.intersects(mpRect))
                            {
                                ((Bridge)bridges.get(i)).setMoved(true);
                                if(bridgePiece.isContinuous())
                                {
                                    if(bridgePiece.getX() > (double)mpRect.x)
                                    {
                                        bridgePiece.setDx(1);
                                        bridgePiece.shoveRight(1.0D);
                                    } else
                                    if(bridgePiece.getX() < (double)mpRect.x)
                                    {
                                        bridgePiece.setDx(-1);
                                        bridgePiece.shoveLeft(1.0D);
                                    }
                                } else
                                if(bridgePiece.getX() > (double)mpRect.x)
                                    bridgePiece.setDx(1);
                                else
                                if(bridgePiece.getX() < (double)mpRect.x)
                                    bridgePiece.setDx(-1);
                            }
                        }

                        for(int j = 0; j < spikes.size(); j++)
                        {
                            Spike mp = (Spike)spikes.get(j);
                            Rectangle mpRect = mp.getBounds();
                            if(bridgeRect.intersects(mpRect))
                            {
                                ((Bridge)bridges.get(i)).setMoved(true);
                                if(bridgePiece.isContinuous())
                                    if(bridgePiece.getX() > (double)mpRect.x)
                                        bridgePiece.shoveRight(1.0D);
                                    else
                                    if(bridgePiece.getX() < (double)mpRect.x)
                                        bridgePiece.shoveLeft(1.0D);
                                bridgePiece.reverseDx();
                            }
                        }

                    }
                    catch(NullPointerException ex)
                    {
                        i++;
                    }

            }
            catch(IllegalArgumentException e)
            {
                System.out.println("Exception Caught!");
                e.printStackTrace();
            }
        }
    }

    private ArrayList paths;
    private ArrayList fallingPaths;
    private ArrayList vines;
    private ArrayList elevators;
    private ArrayList bridges;
    private ArrayList spikes;
    private ArrayList enemies;
    private ArrayList completionPts;
    public boolean loadingLevel;
    Thread mapThread;
    private AudioManager mam;
}
