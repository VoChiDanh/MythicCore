package net.danh.mythicCore.Listeners;

import net.danh.mythicCore.PlayerData.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class JoinQuit implements Listener {

    @EventHandler
    public void onJoin(@NotNull PlayerJoinEvent e) {
        Player p = e.getPlayer();
        new PlayerData(p).loadData();
    }

    @EventHandler
    public void onQuit(@NotNull PlayerQuitEvent e) {
        Player p = e.getPlayer();
        new PlayerData(p).saveData();
    }

    @EventHandler
    public void onLoseHunger(@NotNull FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof org.bukkit.entity.Player)
            e.setCancelled(true);
    }
}
