package net.danh.mythicCore.Compatible.Placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.danh.mythicCore.MythicCore;
import net.danh.mythicCore.PlayerData.PlayerData;
import net.danh.mythicCore.Utils.Number;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PapiParse extends PlaceholderExpansion {
    @NotNull
    @Override
    public String getIdentifier() {
        return "mcore";
    }

    @NotNull
    @Override
    public String getAuthor() {
        return MythicCore.getMythicCore().getPluginMeta().getAuthors().toString();
    }

    @NotNull
    @Override
    public String getVersion() {
        return MythicCore.getMythicCore().getPluginMeta().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Nullable
    @Override
    public String onPlaceholderRequest(Player p, @NotNull String args) {
        if (p != null) {
            if (args.equalsIgnoreCase("class")) {
                return new PlayerData(p).getClassID();
            } else if (args.equalsIgnoreCase("class_name")) {
                return new PlayerData(p).getClassName();
            } else if (args.equalsIgnoreCase("level")) {
                return String.valueOf(new PlayerData(p).getLevel());
            } else if (args.equalsIgnoreCase("level_formatted")) {
                return Number.format(new PlayerData(p).getLevel());
            } else if (args.equalsIgnoreCase("level_fixed")) {
                return Number.formatFull(new PlayerData(p).getLevel());
            } else if (args.equalsIgnoreCase("exp")) {
                return String.valueOf(new PlayerData(p).getExp());
            } else if (args.equalsIgnoreCase("exp_formatted")) {
                return Number.format(new PlayerData(p).getExp());
            } else if (args.equalsIgnoreCase("exp_fixed")) {
                return Number.formatFull(new PlayerData(p).getExp());
            }
        }
        return "";
    }
}
