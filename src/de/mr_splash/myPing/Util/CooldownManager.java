package de.mr_splash.myPing.Util;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;

public class CooldownManager
{

    private HashMap<ProxiedPlayer, Cooldown> cooldowns = new HashMap<ProxiedPlayer, Cooldown>();

    public void addPlayer(ProxiedPlayer player, Cooldown cooldown)
    {
        if(!cooldowns.containsKey(player))
        {
            cooldowns.put(player, cooldown);
        }
    }

    public boolean playerisinlist(ProxiedPlayer player)
    {
        if(cooldowns.containsKey(player))
        {
            return true;
        }
        return false;
    }

    public boolean cooldownRunning(ProxiedPlayer player)
    {
        Cooldown cooldown = cooldowns.get(player);

        if(cooldown == null)
        {
            return false;
        }

        if(!cooldown.isFinished())
        {
            return true;
        }

        return false;
    }

    public boolean isRunning(ProxiedPlayer player)
    {
        if(cooldowns.get(player) != null)
        {
            Cooldown cooldown = cooldowns.get(player);
            return cooldown.isRunning();
        }
        return false;
    }

    public Cooldown getCooldownbyPlayer(ProxiedPlayer player)
    {
        return cooldowns.get(player);
    }

}
