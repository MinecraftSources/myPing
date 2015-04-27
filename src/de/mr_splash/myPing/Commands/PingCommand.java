package de.mr_splash.myPing.Commands;

import de.mr_splash.myPing.myPing;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.protocol.packet.Chat;

public class PingCommand extends Command
{

    private myPing plugin;
    public PingCommand(myPing plugin)
    {
        super("ping");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        if(sender instanceof ProxiedPlayer)
        {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if(plugin.use_permission)
            {
                if(!p.hasPermission("myping.ping"))
                {
                    p.sendMessage(new TextComponent(ChatColor.DARK_RED + "You don't have the permission to do this!"));
                    return;
                }
            }

            if(args.length == 0)
            {
                int ping = p.getPing();
                String msg = new String(plugin.prefix);
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
                    String msg = new String(plugin.other_prefix);
                    msg = msg.replace("%ping%", ping + "");
                    msg = msg.replace("%player%", player.getName());
                    p.sendMessage(new TextComponent(msg));
                    return;
                }
                else
                {
                    if(plugin.not_online != null)
                    {
                        String msg = new String(plugin.not_online);
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
