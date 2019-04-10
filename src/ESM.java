// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ESM.java

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import org.game.engine.*;

public class ESM extends Game
{
    class TimerListener
        implements ActionListener
    {

        public void actionPerformed(ActionEvent e)
        {
            if(mins < 10 && seconds < 10)
                time = (new StringBuilder("0")).append(mins).append(":0").append(seconds).toString();
            else
            if(mins < 10 && seconds > 9)
                time = (new StringBuilder("0")).append(mins).append(":").append(seconds).toString();
            else
            if(mins > 9 && seconds < 10)
                time = (new StringBuilder(String.valueOf(mins))).append(":0").append(seconds).toString();
            else
            if(mins > 9 && seconds > 9)
                time = (new StringBuilder(String.valueOf(mins))).append(":").append(seconds).toString();
            if(seconds == 60)
            {
                seconds = 0;
                mins++;
            }
            if(millis == 1000)
            {
                millis = 0;
                seconds++;
            }
            millis++;
        }


    }


    public static void main(String args[])
    {
        GameStart.start(new ESM(), gameLoop);
    }

    public ESM()
    {
        player = new Player();
        stars = new ArrayList();
        x = 0;
        lives = 3;
        deathStr = "Pwned Bitch";
        starsCollected = 0;
        placedStars = false;
        alpha = 1.0F;
        alphaChange = 0.0025F;
        fadeLL = false;
        level = -5;
        time = "--:--:--";
        highscores = new String[31];
        levelTimes = new int[20];
        seconds = 0;
        millis = 0;
        mins = 0;
        gameState = 0;
        menuOption = 0;
        MAX_MENU_OPTION = 4;
        MAIN_MENU = 0;
        HIGH_SCORES = 1;
        CREDITS = 2;
        NEWGAME = 3;
        INGAME = 4;
        LEVELEND = 5;
        NEXTLEVEL = 6;
        r = new Random();
        title = "Epic Stubble Man!";
        background = Color.black;
        width = 1337;
        height = 795;
        delay = 5;
    }

    public void init()
    {
        for(int i = 0; i < 31; i++)
            highscores[i] = "Level -- Time --:--";

        for(int i = 0; i < 20; i++)
            levelTimes[i] = 0;

        am = new AudioManager();
        timer = new Timer(1, new TimerListener());
        over = false;
        map = new Map();
        loadMenuImages();
        am.startBackgroundMusic(0);
        am.playTrack("welcome");
    }

    public void loadMenuImages()
    {
        ii = new ImageIcon(getClass().getResource("/menu/newgame.png"));
        newGameI = ii.getImage();
        ii = new ImageIcon(getClass().getResource("/menu/newgame_selected.png"));
        newGameSelectedI = ii.getImage();
        ii = new ImageIcon(getClass().getResource("/menu/continue.png"));
        continI = ii.getImage();
        ii = new ImageIcon(getClass().getResource("/menu/continue_selected.png"));
        continSelectedI = ii.getImage();
        ii = new ImageIcon(getClass().getResource("/menu/highscores.png"));
        highscoresI = ii.getImage();
        ii = new ImageIcon(getClass().getResource("/menu/highscores_selected.png"));
        highscoresSelectedI = ii.getImage();
        ii = new ImageIcon(getClass().getResource("/menu/credits.png"));
        creditsI = ii.getImage();
        ii = new ImageIcon(getClass().getResource("/menu/credits_selected.png"));
        creditsSelectedI = ii.getImage();
        ii = new ImageIcon(getClass().getResource("/menu/quit.png"));
        quitI = ii.getImage();
        ii = new ImageIcon(getClass().getResource("/menu/quit_selected.png"));
        quitSelectedI = ii.getImage();
    }

    public void LoadMap()
    {
        map.clear();
        int mapH = 64;
        int mapW = 110;
        String url = (new StringBuilder("maps/Level_")).append(level).append(".txt").toString();
        InputStream in = ESM.class.getResourceAsStream(url);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        int x = 0;
        int y = 0;
        try
        {
            char lastLine[] = (char[])null;
            for(int i = 0; i < mapH; i++)
            {
                char line[] = reader.readLine().toCharArray();
                for(int j = 0; j < mapW; j++)
                {
                    if(line[j] == '#')
                    {
                        player.setDeathHeight(y);
                        map.addPath(x, y);
                    } else
                    if(line[j] == '_')
                    {
                        player.setDeathHeight(y);
                        map.addFallingPath(x, y);
                    } else
                    if(line[j] == '%')
                    {
                        player.setDeathHeight(y);
                        if(j < 109)
                        {
                            if(line[j + 1] == '%')
                            {
                                if((lastLine[j] == '0' && lastLine[j + 1] != '0' || lastLine[j] != '0' && lastLine[j + 1] == '0' || lastLine[j] != '0' && lastLine[j + 1] != '0') && (lastLine[j] == '/' && lastLine[j + 1] != '/' || lastLine[j] != '/' && lastLine[j + 1] == '/' || lastLine[j] != '/' && lastLine[j + 1] != '/'))
                                {
                                    System.out.println((new StringBuilder("RAER!")).append(lastLine[j + 1]).toString());
                                    map.addElevator(x, y, true, false, 1);
                                } else
                                {
                                    map.addElevator(x, y, true, false, -1);
                                }
                                x += 12;
                                j++;
                            }
                        } else
                        if(line[j] == '%')
                            map.addElevator(x, y, false, false, -1);
                    } else
                    if(line[j] == '(')
                    {
                        player.setDeathHeight(y);
                        if(j < 109)
                        {
                            if(line[j + 1] == '(')
                            {
                                if((lastLine[j] == '0' && lastLine[j + 1] != '0' || lastLine[j] != '0' && lastLine[j + 1] == '0' || lastLine[j] != '0' && lastLine[j + 1] != '0') && (lastLine[j] == '/' && lastLine[j + 1] != '/' || lastLine[j] != '/' && lastLine[j + 1] == '/' || lastLine[j] != '/' && lastLine[j + 1] != '/'))
                                    map.addElevator(x, y, true, true, 1);
                                else
                                    map.addElevator(x, y, true, true, -1);
                                x += 12;
                                j++;
                            }
                        } else
                        if(line[j] == '(')
                            map.addElevator(x, y, false, true, -1);
                    } else
                    if(line[j] == '=')
                    {
                        player.setDeathHeight(y);
                        if(j < 109)
                        {
                            if(line[j + 1] == '=')
                            {
                                if(line[j + 2] != '0')
                                    map.addBridge(x, y, true, false, -1);
                                else
                                    map.addBridge(x, y, true, false, 1);
                                x += 12;
                                j++;
                            }
                        } else
                        if(line[j] == '=')
                            map.addBridge(x, y, false, false, -1);
                    } else
                    if(line[j] == ')')
                    {
                        player.setDeathHeight(y);
                        if(j < 109)
                        {
                            if(line[j + 1] == ')')
                            {
                                if(line[j + 2] != '0')
                                    map.addBridge(x, y, true, true, -1);
                                else
                                    map.addBridge(x, y, true, true, 1);
                                x += 12;
                                j++;
                            }
                        } else
                        if(line[j] == ')')
                            map.addBridge(x, y, false, true, -1);
                    } else
                    if(line[j] == '^')
                    {
                        player.setDeathHeight(y);
                        map.addSpike(x, y, true);
                    } else
                    if(line[j] == '.')
                    {
                        player.setDeathHeight(y);
                        map.addSpike(x, y, false);
                    } else
                    if(line[j] == '*')
                    {
                        player.setDeathHeight(y);
                        map.addEnemy(x, y, true);
                    } else
                    if(line[j] == '/')
                    {
                        player.setDeathHeight(y);
                        map.addGas(x, y);
                    } else
                    if(line[j] == '+')
                    {
                        player.setDeathHeight(y);
                        if(!placedStars)
                        {
                            starsPlaced++;
                            stars.add(new Star(x, y));
                        }
                    } else
                    if(line[j] == '$')
                        player.setStartPoint(x, y);
                    else
                    if(line[j] == '@')
                        compPoint = new CompletionPoint(x, y);
                    x += 12;
                }

                lastLine = line;
                x = 0;
                y += 12;
            }

            reader.close();
            map.loadingLevel = false;
            placedStars = true;
            map.startThread();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void update()
    {
        if(gameState == NEWGAME)
        {
            LoadMap();
            player.reset();
            timer.start();
            am.playStoryTrack("keys");
            gameState = INGAME;
        } else
        if(gameState == INGAME)
        {
            if(stars.isEmpty())
                compPoint.setActive();
            if(player.dead)
            {
                am.playTrack("pwned");
                lives--;
                fadeLL = false;
                fadeLL = true;
                llX = (int)Math.round(player.getX());
                llY = Math.round(player.getY());
                if(lives < 0)
                {
                    am.playTrack("evil_laugh");
                    level--;
                    lives = 3;
                    if(!stars.isEmpty())
                        stars.clear();
                    starsCollected = 0;
                    starsPlaced = 0;
                    placedStars = false;
                }
                map.loadingLevel = true;
                map.clear();
                LoadMap();
                player.restart();
            }
            if(!map.loadingLevel)
            {
                map.update(player);
                player.update();
            }
            if(starsCollected == starsPlaced)
            {
                if(player.getBounds().intersects(compPoint.getBounds()))
                {
                    timer.stop();
                    try
                    {
                        if(level > 0)
                        {
                            BufferedWriter out = new BufferedWriter(new FileWriter("times.txt", true));
                            out.write((new StringBuilder(String.valueOf(level))).append("\n").toString());
                            out.write((new StringBuilder(String.valueOf(mins))).append("\n").toString());
                            out.write((new StringBuilder(String.valueOf(seconds))).append("\n").toString());
                            out.close();
                        }
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                    map.clear();
                    gameState = LEVELEND;
                }
            } else
            {
                for(int i = 0; i < stars.size(); i++)
                    if(player.getBounds().intersects(((Star)stars.get(i)).getBounds()))
                    {
                        starsCollected++;
                        am.playTrack("star");
                        stars.remove(i);
                    }

            }
        } else
        if(gameState == NEXTLEVEL)
        {
            if(level <= 6)
            {
                am.playTrack("next_level");
                level++;
                lives = 3;
                millis = 0;
                seconds = 0;
                mins = 0;
                starsCollected = 0;
                starsPlaced = 0;
                placedStars = false;
                map.loadingLevel = true;
                LoadMap();
                player.reset();
                timer.start();
            }
            gameState = INGAME;
        }
    }

    public void draw(Graphics2D g2d)
    {
        if(gameState == MAIN_MENU)
        {
            if(menuOption == 0)
            {
                g2d.drawImage(newGameSelectedI, 385, 100, null);
                g2d.drawImage(continI, 400, 200, null);
                g2d.drawImage(highscoresI, 400, 300, null);
                g2d.drawImage(creditsI, 400, 400, null);
                g2d.drawImage(quitI, 400, 500, null);
            } else
            if(menuOption == 1)
            {
                g2d.drawImage(newGameI, 385, 100, null);
                g2d.drawImage(continSelectedI, 400, 200, null);
                g2d.drawImage(highscoresI, 400, 300, null);
                g2d.drawImage(creditsI, 400, 400, null);
                g2d.drawImage(quitI, 400, 500, null);
            } else
            if(menuOption == 2)
            {
                g2d.drawImage(newGameI, 385, 100, null);
                g2d.drawImage(continI, 400, 200, null);
                g2d.drawImage(highscoresSelectedI, 400, 300, null);
                g2d.drawImage(creditsI, 400, 400, null);
                g2d.drawImage(quitI, 400, 500, null);
            } else
            if(menuOption == 3)
            {
                g2d.drawImage(newGameI, 385, 100, null);
                g2d.drawImage(continI, 400, 200, null);
                g2d.drawImage(highscoresI, 400, 300, null);
                g2d.drawImage(creditsSelectedI, 400, 400, null);
                g2d.drawImage(quitI, 400, 500, null);
            } else
            if(menuOption == 4)
            {
                g2d.drawImage(newGameI, 385, 100, null);
                g2d.drawImage(continI, 400, 200, null);
                g2d.drawImage(highscoresI, 400, 300, null);
                g2d.drawImage(creditsI, 400, 400, null);
                g2d.drawImage(quitSelectedI, 400, 500, null);
            }
        } else
        if(gameState == HIGH_SCORES)
        {
            ImageIcon ii = new ImageIcon(getClass().getResource("menu/highscores_screen_3.png"));
            image = ii.getImage();
            g2d.drawImage(image, 610, 50, null);
            g2d.setFont(new Font("Quartz MS", 0, 24));
            int i2 = -1;
            for(int i = 0; i < highscores.length; i++)
            {
                int y = 0;
                int x = 175;
                if(i < 15)
                {
                    if(i == 0)
                        y = 50;
                    else
                        y = i * 50 + 50;
                } else
                {
                    if(i2 < 0)
                        y = 50;
                    else
                        y = i2 * 50 + 50;
                    i2++;
                }
                if(i >= 15)
                    x += 700;
                GradientPaint gradColor = new GradientPaint(x - 2, y - 22, new Color(r.nextInt(150) + 105, 0, 0), (x - 2) + 240, (y - 22) + 26, new Color(0, r.nextInt(255), r.nextInt(100) + 155), true);
                g2d.setPaint(gradColor);
                g2d.drawRect(x - 2, y - 22, 250, 26);
                g2d.drawString(highscores[i], x, y);
            }

        } else
        if(gameState == CREDITS)
        {
            ImageIcon ii = new ImageIcon(getClass().getResource("menu/credits_screen.png"));
            image = ii.getImage();
            g2d.drawImage(image, 168, 0, null);
        } else
        if(gameState == INGAME)
        {
            drawTutorial(g2d);
            drawLevel(g2d);
            drawLives(g2d);
            fadeText(g2d);
            for(int i = 0; i < stars.size(); i++)
                g2d.drawImage(((Star)stars.get(i)).getImage(), ((Star)stars.get(i)).getX(), ((Star)stars.get(i)).getY(), null);

            if(compPoint != null)
                g2d.drawImage(compPoint.getImage(), compPoint.getX(), compPoint.getY(), null);
            if(!map.loadingLevel)
            {
                map.draw(g2d);
                if(player != null)
                    player.draw(g2d);
            }
        } else
        if(gameState == LEVELEND)
        {
            GradientPaint gradColor = new GradientPaint(0.0F, 100F, new Color(r.nextInt(150) + 105, 0, 0), 100F, 200F, new Color(0, r.nextInt(255), r.nextInt(100) + 155), true);
            g2d.setPaint(gradColor);
            g2d.setFont(new Font("Quartz MS", 1, 48));
            g2d.drawString((new StringBuilder("LEVEL ")).append(level).append("/20 COMPLETED!").toString(), width / 2 - 300, 250);
            GradientPaint gradColor2 = new GradientPaint(0.0F, 100F, new Color(0, r.nextInt(150) + 50, 0), 100F, 200F, new Color(0, r.nextInt(100) + 155, 0), true);
            g2d.setPaint(gradColor2);
            g2d.setFont(new Font("Quartz MS", 1, 36));
            g2d.drawString((new StringBuilder("Time- ")).append(time).toString(), width / 2 - 125, 350);
            g2d.setFont(new Font("Quartz MS", 1, 32));
            g2d.setColor(Color.white);
            g2d.drawString("press 'Enter' to continue", width / 2 - 225, 450);
        }
    }

    private void drawTutorial(Graphics2D g2d)
    {
        if(level <= 0)
        {
            g2d.setFont(new Font("Quartz MS", 1, 28));
            g2d.setColor(Color.green);
            g2d.drawString("Tutorial Level", 1065, 25);
        }
    }

    private void drawLevel(Graphics2D g2d)
    {
        if(level != 6 && level > 0)
        {
            g2d.setFont(new Font("Quartz MS", 1, 28));
            g2d.setColor(Color.lightGray);
            g2d.drawString((new StringBuilder("Level: ")).append(level).toString(), 1150, 35);
        }
    }

    private void fadeText(Graphics2D g2d)
    {
        if(fadeLL)
        {
            g2d.setComposite(AlphaComposite.getInstance(3, alpha));
            g2d.setPaint(Color.red);
            g2d.setFont(new Font("Arial", 1, 54));
            g2d.drawString("\u2665", llX, llY);
            g2d.setFont(new Font("Quartz MS", 1, 26));
            g2d.drawString(deathStr, llX - 80, llY + 24);
            g2d.setComposite(AlphaComposite.getInstance(3, 1.0F));
            reduceAlpha();
            if(alpha <= alphaChange)
            {
                alpha = 1.0F;
                fadeLL = false;
            }
            deathStr = "Pwned Bitch";
        }
    }

    private void drawStarsCollected(Graphics2D g2d)
    {
        g2d.setColor(Color.lightGray);
        g2d.setFont(new Font("Quartz MS", 1, 20));
        g2d.drawString((new StringBuilder()).append(starsCollected).toString(), 500, 740);
        g2d.drawString((new StringBuilder()).append(starsPlaced).toString(), 540, 740);
        g2d.setColor(Color.cyan);
        g2d.setFont(new Font("Quartz MS", 1, 17));
        g2d.drawString("of    Stars", 517, 740);
    }

    private void drawLives(Graphics2D g2d)
    {
        g2d.setFont(new Font("Arial", 2, 36));
        g2d.setColor(Color.red);
        if(lives == 1)
            g2d.drawString("\u2665", 10, 35);
        else
        if(lives == 2)
            g2d.drawString("\u2665 \u2665", 10, 35);
        else
        if(lives == 3)
            g2d.drawString("\u2665 \u2665 \u2665", 10, 35);
        g2d.setFont(new Font("Quartz MS", 1, 28));
    }

    private void reduceAlpha()
    {
        if(alpha > alphaChange)
            alpha -= alphaChange;
    }

    private void save()
    {
        try
        {
            BufferedWriter out = new BufferedWriter(new FileWriter("save.txt", false));
            out.write((new StringBuilder()).append(level).append("\n").toString());
            out.write((new StringBuilder()).append(lives).toString());
            out.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        stars.clear();
        starsPlaced = 0;
        starsCollected = 0;
        placedStars = false;
    }

    private void load()
    {
        String url = "save.txt";
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(new File(url)));
            String temp = reader.readLine();
            level = Integer.parseInt(temp);
            temp = reader.readLine();
            lives = Integer.parseInt(temp);
            reader.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(NumberFormatException e)
        {
            gameState = NEWGAME;
        }
    }

    private void loadHighscores()
    {
        String url = "times.txt";
        try
        {
            String score = "";
            int index = 0;
            int currScore = 0;
            int totalSeconds = 0;
            BufferedReader reader = new BufferedReader(new FileReader(new File(url)));
            String temp;
            while((temp = reader.readLine()) != null) 
                if(++index == 1)
                    score = (new StringBuilder(String.valueOf(score))).append("Level ").append(currScore + 1).toString();
                else
                if(index == 2)
                {
                    score = (new StringBuilder(String.valueOf(score))).append("  Time- ").toString();
                    if(Integer.parseInt(temp) > 0)
                    {
                        score = (new StringBuilder(String.valueOf(score))).append(temp).append(":").toString();
                        totalSeconds += Integer.parseInt(temp) * 60;
                    } else
                    {
                        score = (new StringBuilder(String.valueOf(score))).append("00:").toString();
                    }
                } else
                if(index == 3)
                {
                    if(Integer.parseInt(temp) < 10)
                        score = (new StringBuilder(String.valueOf(score))).append("0").append(temp).toString();
                    else
                        score = (new StringBuilder(String.valueOf(score))).append(temp).toString();
                    totalSeconds += Integer.parseInt(temp);
                    if(levelTimes[currScore] < totalSeconds)
                    {
                        levelTimes[currScore] = totalSeconds;
                        highscores[currScore] = score;
                    }
                    System.out.println(score);
                    score = "";
                    index = 0;
                    currScore++;
                }
            reader.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void keyPressed(KeyEvent e)
    {
        switch(e.getKeyCode())
        {
        case 27: // '\033'
            if(gameState == INGAME)
            {
                save();
                map.clear();
                timer.stop();
            }
            am.playTrack("menu_exited");
            gameState = MAIN_MENU;
            break;

        case 10: // '\n'
            if(gameState == LEVELEND)
                gameState = NEXTLEVEL;
            break;
        }
        if(gameState == MAIN_MENU)
            switch(e.getKeyCode())
            {
            case 87: // 'W'
                am.playTrack("menu_changed");
                if(menuOption == 0)
                    menuOption = MAX_MENU_OPTION;
                else
                    menuOption--;
                break;

            case 83: // 'S'
                am.playTrack("menu_changed");
                if(menuOption == MAX_MENU_OPTION)
                    menuOption = 0;
                else
                    menuOption++;
                break;

            case 10: // '\n'
                am.playTrack("menu_entered");
                if(menuOption == MAX_MENU_OPTION)
                    System.exit(0);
                else
                if(menuOption == 0)
                {
                    level = -5;
                    lives = 3;
                    player.dead = false;
                    gameState = NEWGAME;
                } else
                if(menuOption == 1)
                {
                    load();
                    gameState = NEWGAME;
                } else
                if(menuOption == 2)
                {
                    loadHighscores();
                    gameState = HIGH_SCORES;
                } else
                if(menuOption == 3)
                    gameState = CREDITS;
                break;
            }
        else
        if(gameState == HIGH_SCORES)
            e.getKeyCode();
        else
        if(gameState == CREDITS)
            e.getKeyCode();
        else
        if(gameState == INGAME)
            switch(e.getKeyCode())
            {
            case 83: // 'S'
            case 87: // 'W'
            default:
                break;

            case 65: // 'A'
                player.setDx(-1);
                player.keyPressed = true;
                player.movingRight(false);
                break;

            case 68: // 'D'
                player.setDx(1);
                player.keyPressed = true;
                player.movingRight(true);
                break;

            case 32: // ' '
                if(player.grounded)
                {
                    player.jump();
                    am.playTrack("jump");
                }
                break;

            case 16: // '\020'
                player.setWalk();
                break;

            case 82: // 'R'
                player.dead = true;
                break;

            case 122: // 'z'
                timer.stop();
                player.dead = true;
                level--;
                lives = 4;
                placedStars = false;
                starsPlaced = 0;
                starsCollected = 0;
                stars.clear();
                break;

            case 123: // '{'
                timer.stop();
                player.dead = true;
                level++;
                lives = 4;
                placedStars = false;
                starsPlaced = 0;
                starsCollected = 0;
                stars.clear();
                break;
            }
    }

    public void keyReleased(KeyEvent e)
    {
        if(gameState == MAIN_MENU)
            e.getKeyCode();
        else
        if(gameState == HIGH_SCORES)
            e.getKeyCode();
        else
        if(gameState == CREDITS)
            e.getKeyCode();
        else
        if(gameState == INGAME)
            switch(e.getKeyCode())
            {
            case 65: // 'A'
                player.keyPressed = false;
                break;

            case 68: // 'D'
                player.keyPressed = false;
                break;

            case 16: // '\020'
                player.setRun();
                break;
            }
    }

    private static GameLoop gameLoop = new GameLoop();
    private Map map;
    private Player player;
    private CompletionPoint compPoint;
    private ArrayList stars;
    int x;
    private int lives;
    private String deathStr;
    private int starsCollected;
    private int starsPlaced;
    private boolean placedStars;
    private float alpha;
    private float alphaChange;
    private Image image;
    private boolean fadeLL;
    private int llX;
    private int llY;
    public int level;
    private final int MAX_LEVEL = 6;
    private final int SPRITE_WIDTH = 12;
    private final int SPRITE_HEIGHT = 12;
    private AudioManager am;
    private Timer timer;
    private String time;
    private String highscores[];
    private int levelTimes[];
    private int seconds;
    private int millis;
    private int mins;
    private int gameState;
    private int menuOption;
    private int MAX_MENU_OPTION;
    private int MAIN_MENU;
    private int HIGH_SCORES;
    private int CREDITS;
    private int NEWGAME;
    private int INGAME;
    private int LEVELEND;
    private int NEXTLEVEL;
    private Image newGameI;
    private Image newGameSelectedI;
    private Image continI;
    private Image continSelectedI;
    private Image highscoresI;
    private Image highscoresSelectedI;
    private Image creditsI;
    private Image creditsSelectedI;
    private Image quitI;
    private Image quitSelectedI;
    private Image menuImage;
    private ImageIcon ii;
    Random r;








}
