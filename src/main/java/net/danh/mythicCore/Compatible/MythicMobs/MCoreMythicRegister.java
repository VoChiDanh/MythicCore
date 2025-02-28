package net.danh.mythicCore.Compatible.MythicMobs;

import io.lumine.mythic.bukkit.events.MythicConditionLoadEvent;
import io.lumine.mythic.bukkit.events.MythicMechanicLoadEvent;
import net.danh.mythicCore.Compatible.MythicMobs.Condition.MCoreClass_Condition;
import net.danh.mythicCore.Compatible.MythicMobs.Condition.MCoreExp_Condition;
import net.danh.mythicCore.Compatible.MythicMobs.Condition.MCoreLevel_Condition;
import net.danh.mythicCore.Compatible.MythicMobs.Mechanic.MCoreExp_Mechanic;
import net.danh.mythicCore.Compatible.MythicMobs.Mechanic.MCoreLevel_Mechanic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class MCoreMythicRegister implements Listener {

    @EventHandler
    public void onMCoreMechanicRegister(@NotNull MythicMechanicLoadEvent e) {
        if (e.getMechanicName().equalsIgnoreCase("mcore_level")) {
            e.register(new MCoreLevel_Mechanic(e.getConfig()));
        } else if (e.getMechanicName().equalsIgnoreCase("mcore_exp")) {
            e.register(new MCoreExp_Mechanic(e.getConfig()));
        }
    }

    @EventHandler
    public void onMCoreConditionRegister(@NotNull MythicConditionLoadEvent e) {
        if (e.getConditionName().equalsIgnoreCase("mcore_level")) {
            e.register(new MCoreLevel_Condition(e.getConfig()));
        } else if (e.getConditionName().equalsIgnoreCase("mcore_exp")) {
            e.register(new MCoreExp_Condition(e.getConfig()));
        } else if (e.getConditionName().equalsIgnoreCase("mcore_class")) {
            e.register(new MCoreClass_Condition(e.getConfig()));
        }
    }
}
