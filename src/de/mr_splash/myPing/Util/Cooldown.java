package de.mr_splash.myPing.Util;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.util.concurrent.TimeUnit;

public class Cooldown
{


    private ProxiedPlayer player;
    private int time;
    private int timeleft;
    private int interval;
    private int delay;
    private TimeUnit timeUnit;
    private boolean running;
    private boolean runned;
    private boolean finished;
    private ScheduledTask runnable;


    public Cooldown(ProxiedPlayer player, int time, TimeUnit timeUnit, int interval, int delay)
    {
        this.finished = false;
        this.delay = delay;
        this.interval = interval;
        this.timeUnit = timeUnit;
        this.player = player;
        this.time = time;
        running = false;
    }

    public boolean isFinished()
    {
        return finished;
    }

    public void setTimeleft(int timeleft)
    {
        this.timeleft = timeleft;
    }

    public int getTimeleft()
    {
        return timeleft;
    }

    public ProxiedPlayer getPlayer()
    {
        return player;
    }

    public int getTime()
    {
        return time;
    }

    public boolean isRunning()
    {
        return running;
    }

    public void stop()
    {
        running = false;
    }

    public void run()
    {
        if(!runned)
        {
            timeleft = time;
            runned = true;
        }
        running = true;
        runnable = ProxyServer.getInstance().getScheduler().schedule(de.mr_splash.myPing.myPing.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                if(running)
                {
                    if(timeleft > 0)
                    {
                        timeleft--;
                    }
                    else
                    {
                        running = false;
                        finished = true;
                        ProxyServer.getInstance().getScheduler().cancel(runnable);
                    }
                }
                else
                {
                    ProxyServer.getInstance().getScheduler().cancel(runnable);
                }
            }
        }, delay, interval, timeUnit);
    }


    public void runAsync()
    {
        /*
        Work in progress
         */
        ProxyServer.getInstance().getScheduler().runAsync(de.mr_splash.myPing.myPing.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                run();
            }
        });
    }

    public void restart()
    {
        running = false;
        finished = false;
        timeleft = time;
        if(runnable != null)
        {
            ProxyServer.getInstance().getScheduler().cancel(runnable);
        }
        run();
    }


}
