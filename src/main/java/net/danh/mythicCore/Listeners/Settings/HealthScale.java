package net.danh.mythicCore.Listeners.Settings;

import net.danh.mythicCore.MythicCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class HealthScale implements Listener {
    private final double scale;
    private final int delay;

    public HealthScale(double scale, int delay) {
        this.scale = scale;
        this.delay = delay;
    }

    @EventHandler
    private void onJoin(@NotNull PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Bukkit.getScheduler().runTaskLater(MythicCore.getMythicCore(), () -> {
            player.setHealthScaled(true);
            player.setHealthScale(scale);
        }, delay);
    }
}
