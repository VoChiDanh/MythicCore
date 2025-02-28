package net.danh.mythicCore.Compatible.MythicMobs.Condition;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.conditions.IEntityCondition;
import net.danh.mythicCore.PlayerData.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MCoreClass_Condition implements IEntityCondition {

    protected final String classID;

    public MCoreClass_Condition(@NotNull MythicLineConfig config) {
        classID = config.getString(new String[]{"id"});
    }

    @Override
    public boolean check(@NotNull AbstractEntity abstractEntity) {
        Player p = Bukkit.getPlayer(abstractEntity.getName());
        if (p != null) {
            return new PlayerData(p).getClassID().equalsIgnoreCase(classID);
        }
        return false;
    }
}
