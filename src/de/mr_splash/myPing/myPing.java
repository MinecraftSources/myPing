package de.mr_splash.myPing;

import de.mr_splash.myPing.Commands.PingCommand;
import de.mr_splash.myPing.Commands.PingreloadCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class myPing extends Plugin
{


    @Override
    public void onEnable()
    {
        myPing = this;
        registerCommands();
        loadcfg();
    }

    public static myPing getInstance()
    {
        return myPing;
    }


    private void registerCommands()
    {
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new PingCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new PingreloadCommand());
    }



    public void loadcfg()
    {
        try
        {
            if(!getDataFolder().exists())
            {
                getDataFolder().mkdir();
            }
            File file = new File(getDataFolder().getPath(), "config.yml");
            if(!file.exists())
            {
                file.createNewFile();
            }

            Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            if(configuration.get("settings.prefix") == null)
            {
                configuration.set("settings.prefix", "&6Your ping: &b%ping% ms");
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
            }
            if(configuration.get("settings.other_prefix") == null)
            {
                configuration.set("settings.other_prefix", "&6%player%&3 his ping is:&b %ping%");
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
            }
            if(configuration.get("settings.not_online") == null)
            {
                configuration.set("settings.not_online", "&cThe player %player% is not online!");
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
            }
            if(configuration.get("settings.use_permission") == null)
            {
                configuration.set("settings.use_permission", false);
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
            }
            if(configuration.get("settings.use_cooldown") == null)
            {
                configuration.set("settings.use_cooldown", false);
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
            }
            if(configuration.get("settings.cooldown_message") == null)
            {
                configuration.set("settings.cooldown_message", "&cPlease wait %time% seconds before checking again");
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
            }
            if(configuration.get("settings.time") == null)
            {
                configuration.set("settings.time", 5);
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
            }

            cooldown_time = configuration.getInt("settings.time");
            prefix = ChatColor.translateAlternateColorCodes('&', configuration.getString("settings.prefix"));
            other_prefix = ChatColor.translateAlternateColorCodes('&', configuration.getString("settings.other_prefix"));
            not_online = ChatColor.translateAlternateColorCodes('&', configuration.getString("settings.not_online"));
            cooldown_message = ChatColor.translateAlternateColorCodes('&', configuration.getString("settings.cooldown_message"));
            use_permission = configuration.getBoolean("settings.use_permission");
            use_cooldown = configuration.getBoolean("settings.use_cooldown");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String prefix;
    private String other_prefix;
    private String not_online;

    public String getCooldown_message()
    {
        return cooldown_message;
    }

    public int getCooldown_time()
    {
        return cooldown_time;
    }

    public boolean isUse_cooldown()
    {
        return use_cooldown;
    }

    public boolean isUse_permission()
    {
        return use_permission;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public String getOther_prefix()
    {
        return other_prefix;
    }

    public String getNot_online()
    {
        return not_online;
    }

    private String cooldown_message;
    private int cooldown_time;
    private boolean use_cooldown;
    private boolean use_permission;
    private static myPing myPing;

}
