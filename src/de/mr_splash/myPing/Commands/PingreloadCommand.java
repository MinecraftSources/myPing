package de.mr_splash.myPing.Commands;

import de.mr_splash.myPing.myPing;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class PingreloadCommand extends Command
{
    public PingreloadCommand()
    {
        super("pingreload");
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        if(sender.hasPermission("myping.reload"))
        {
            myPing.getInstance().getInstance().loadcfg();
            sender.sendMessage(new TextComponent(ChatColor.GREEN + "Successful reloadet config"));
        }
        else
        {
            sender.sendMessage(new TextComponent(ChatColor.RED + "You do not have the permission to do this"));
        }
    }
}
