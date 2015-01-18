package de.mr_splash.myPing.Commands;

import de.mr_splash.myPing.myPing;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

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
            if(args.length == 0)
            {
                int ping = p.getPing();
                String msg = new String(plugin.prefix);
                msg = msg.replace("%ping%", ping + "");
                p.sendMessage(new TextComponent(msg));
            }
            else
            {
                p.sendMessage(new TextComponent(ChatColor.RED + "/ping"));
            }
        }
        else
        {
            sender.sendMessage(new TextComponent("You can't do that!"));
        }
    }

}
