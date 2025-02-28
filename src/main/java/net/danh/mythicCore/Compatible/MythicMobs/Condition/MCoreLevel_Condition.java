package net.danh.mythicCore.Compatible.MythicMobs.Condition;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.conditions.IEntityCondition;
import net.danh.mythicCore.PlayerData.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MCoreLevel_Condition implements IEntityCondition {

    protected final int level;

    public MCoreLevel_Condition(@NotNull MythicLineConfig config) {
        level = config.getInteger(new String[]{"amount", "a"}, 1);
    }

    @Override
    public boolean check(@NotNull AbstractEntity abstractEntity) {
        Player p = Bukkit.getPlayer(abstractEntity.getName());
        if (p != null) {
            return new PlayerData(p).getLevel() >= level;
        }
        return false;
    }
}
