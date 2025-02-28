package net.danh.mythicCore.PlayerData;

import net.danh.mythicCore.Utils.Chat;
import net.xconfig.bukkit.model.SimpleConfigurationManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ClassManager {

    private final String file;

    public ClassManager(String file) {
        this.file = file;
    }

    public String getFileName() {
        return file;
    }

    public FileConfiguration getFileManager() {
        return SimpleConfigurationManager.get().get("Class/" + file + ".yml");
    }

    public String getClassName() {
        return Chat.normalColorize(getFileManager().getString("name", file));
    }

    public String getExpSource() {
        return getFileManager().getString("exp-source");
    }

    public boolean getDefault() {
        return getFileManager().contains("default") && getFileManager().getBoolean("default");
    }

    public int getMaxLevel() {
        return ExpData.getMaxLevel(getExpSource());
    }

    public int getMaxExp(int level) {
        return ExpData.getExperience(getExpSource(), level);
    }

    public List<String> getListSkill() {
        return getFileManager().getStringList("skills");
    }

    public List<String> getSkillName() {
        List<String> skills = new ArrayList<>();
        getListSkill().forEach(s -> skills.add(s.split(";")[0]));
        return skills;
    }

    public int getReqSkills(String skill) {
        for (String s : getListSkill()) {
            if (skill.equalsIgnoreCase(s.split(";")[0])) {
                return Integer.parseInt(s.split(";")[1]);
            }
        }
        return 0;
    }

    public void castSkill(Player p, String skill) {
//        int level = new PlayerData(p).getLevel();
//        int req = getReqSkills(skill);
//        CooldownMap map = new CooldownMap();
//        double remaining = map.getCooldown(skill);
//        boolean isOnCooldown = map.isOnCooldown(skill);
//        if (!isOnCooldown) {
//            if (getSkillName().contains(skill)) {
//                if (level >= req) {
//                    MythicBukkit.inst().getSkillManager().getSkill(skill).ifPresentOrElse(value -> {
//                        MythicBukkit.inst().getAPIHelper().castSkill(p, value.getInternalName());
//                        map.applyCooldown(skill, value.getConfig().getDouble("Cooldown"));
//                    }, () -> {
//                        sendPlayerMessage(p, File.getMessage().getConfig().getString("skill_is_null", "&cSkill #name# is null").replace("#name#", skill));
//                    });
//                } else {
//                    sendPlayerMessage(p, File.getMessage().getConfig().getString("not_enough_level", "&cYou need reach level #level#").replace("#level#", String.valueOf(req)));
//                }
//            }
//        } else {
//            sendPlayerMessage(p, File.getMessage().getConfig().getString("cooldown_skill", "&cYou need wait %time%s to cast skill again").replace("%time%", String.valueOf(remaining)));
//        }
    }
}
