package de.mr_splash.myPing.Commands;

import de.mr_splash.myPing.Util.Cooldown;
import de.mr_splash.myPing.Util.CooldownManager;
import de.mr_splash.myPing.myPing;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.protocol.packet.Chat;

import java.util.concurrent.TimeUnit;

public class PingCommand extends Command
{

    private myPing plugin = myPing.getInstance();
    private CooldownManager cooldownManager = new CooldownManager();
    public PingCommand()
    {
        super("ping");
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        if(sender instanceof ProxiedPlayer)
        {
            ProxiedPlayer p = (ProxiedPlayer) sender;

            if(plugin.isUse_permission())
            {
                if(!p.hasPermission("myping.ping"))
                {
                    p.sendMessage(new TextComponent(ChatColor.DARK_RED + "You don't have the permission to do this!"));
                    return;
                }
            }

            if(plugin.isUse_cooldown() && !p.hasPermission("myping.bypasscooldown"))
            {
                if(cooldownManager.playerisinlist(p))
                {
                    if(!cooldownManager.getCooldownbyPlayer(p).isFinished())
                    {
                        int timeleft = cooldownManager.getCooldownbyPlayer(p).getTimeleft();
                        String msg = plugin.getCooldown_message();
                        msg = msg.replace("%time%", timeleft + "");
                        p.sendMessage(new TextComponent(msg));
                        return;
                    }
                    cooldownManager.getCooldownbyPlayer(p).restart();
                }
                else
                {
                    int time = plugin.getCooldown_time();
                    Cooldown cooldown = new Cooldown(p, time, TimeUnit.SECONDS, 1, 0);
                    cooldownManager.addPlayer(p, cooldown);
                    cooldown.run();
                }
            }

            if(args.length == 0)
            {
                int ping = p.getPing();
                String msg = plugin.getPrefix();
                msg = msg.replace("%ping%", ping + "");
                p.sendMessage(new TextComponent(msg));
                return;
            }
            else
            {
                ProxiedPlayer player = ProxyServer.getInstance().getPlayer(args[0]);
                if(player != null)
                {
                    int ping = player.getPing();
                    String msg = plugin.getOther_prefix();
                    msg = msg.replace("%ping%", ping + "");
                    msg = msg.replace("%player%", player.getName());
                    p.sendMessage(new TextComponent(msg));
                    return;
                }
                else
                {
                    if(plugin.getNot_online() != null)
                    {
                        String msg = plugin.getNot_online();
                        msg = msg.replace("%player%", args[0]);
                        p.sendMessage(new TextComponent(msg));
                    }
                    else
                    {
                        p.sendMessage(new TextComponent(ChatColor.RED + "The player " + args[0] + " isn't online!"));
                    }
                    return;
                }
            }
        }
        else
        {
            sender.sendMessage(new TextComponent("You can't do that!"));
        }
    }

}
