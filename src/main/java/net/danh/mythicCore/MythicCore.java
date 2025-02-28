package net.danh.mythicCore;

import net.danh.mythicCore.CMD.MCCMD;
import net.danh.mythicCore.Compatible.MythicMobs.MCoreMythicRegister;
import net.danh.mythicCore.Compatible.Placeholder.PapiParse;
import net.danh.mythicCore.Listeners.JoinQuit;
import net.danh.mythicCore.NMS.NMSAssistant;
import net.danh.mythicCore.PlayerData.ExpData;
import net.danh.mythicCore.PlayerData.PlayerData;
import net.danh.mythicCore.Resources.File;
import net.xconfig.bukkit.model.SimpleConfigurationManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public final class MythicCore extends JavaPlugin {

    private static MythicCore mythicCore;

    public static MythicCore getMythicCore() {
        return mythicCore;
    }

    @Override
    public void onEnable() {
        mythicCore = this;
        getLogger().log(Level.INFO, "Server NMS: " + new NMSAssistant().getNMSVersion());
        SimpleConfigurationManager.register(this);
        File.createFiles();
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().log(Level.INFO, "Compatible with PlaceholderAPI");
            new PapiParse().register();
        }
        if (getServer().getPluginManager().getPlugin("MythicMobs") != null) {
            getLogger().log(Level.INFO, "Compatible with MythicMobs");
            registerEvents(new MCoreMythicRegister());
        }
        try {
            ExpData.load("exp.txt");
            ExpData.loadExpSource();
        } catch (IOException e) {
            getLogger().warning("Has issue with ExpBase (exp.txt)");
        }
        try {
            ExpData.loadClass();
        } catch (IOException e) {
            getLogger().warning("Has issue with Class");
        }
        registerEvents(new JoinQuit());
        new MCCMD();
        Executors.newScheduledThreadPool(Integer.MAX_VALUE).scheduleAtFixedRate(() -> Bukkit.getOnlinePlayers().forEach(p -> new PlayerData(p).syncXPBar()), 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void onDisable() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            new PlayerData(p).saveData();
        }
        File.saveFile();
    }


    private void registerEvents(Listener... listeners) {
        Arrays.asList(listeners).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }
}
