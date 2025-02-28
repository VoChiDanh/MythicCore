package net.danh.mythicCore.Compatible.MythicMobs.Mechanic;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import net.danh.mythicCore.PlayerData.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MCoreExp_Mechanic implements ITargetedEntitySkill {

    protected final String type;
    protected final int amount;

    public MCoreExp_Mechanic(@NotNull MythicLineConfig config) {
        type = config.getString(new String[]{"type", "t"});
        amount = config.getInteger(new String[]{"amount", "a"}, 1);
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata skillMetadata, @NotNull AbstractEntity abstractEntity) {
        Player p = Bukkit.getPlayer(abstractEntity.getName());
        if (p != null) {
            if (type.equalsIgnoreCase("add")) {
                new PlayerData(p).editExp(amount);
                return SkillResult.SUCCESS;
            } else if (type.equalsIgnoreCase("remove")) {
                new PlayerData(p).editExp(-amount);
                return SkillResult.SUCCESS;
            } else if (type.equalsIgnoreCase("set")) {
                new PlayerData(p).setExp(amount);
                return SkillResult.SUCCESS;
            }
        }
        return SkillResult.CONDITION_FAILED;
    }
}