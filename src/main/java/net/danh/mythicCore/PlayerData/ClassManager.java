package net.danh.mythicCore.PlayerData;

import io.lumine.mythic.bukkit.MythicBukkit;
import net.danh.mythicCore.Resources.File;
import net.danh.mythicCore.Utils.Chat;
import net.danh.mythicCore.Utils.Number;
import net.xconfig.bukkit.model.SimpleConfigurationManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

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

    public long getMaxLevel() {
        return ExpData.getMaxLevel(getExpSource());
    }

    public long getMaxExp(long level) {
        return ExpData.getExperience(getExpSource(), level);
    }

    public List<String> getListSkill() {
        return Objects.requireNonNull(getFileManager().getConfigurationSection("skills")).getKeys(false).stream().toList();
    }

    public String getSkillName(String skillID) {
        return Chat.normalColorize(getFileManager().getString("skills." + skillID + ".display"));
    }

    public long getReqSkills(String skillID) {
        return getFileManager().getLong("skills." + skillID + ".level");
    }

    public void castSkill(Player p, String skill) {
        String mythicSkill = getFileManager().getString("skills." + skill + ".id");
        PlayerData playerData = new PlayerData(p);
        long level = playerData.getLevel();
        long reqLevel = getReqSkills(skill);
        if (level >= reqLevel) {
            MythicBukkit.inst().getSkillManager().getSkill(mythicSkill).ifPresentOrElse(value -> MythicBukkit.inst().getAPIHelper().castSkill(p, value.getInternalName()), () -> Chat.sendMessage(p, Chat.replace(File.getMessage().getString("user.skill_is_null"), "{name}", getSkillName(skill))));
        } else {
            Chat.sendMessage(p, Chat.replace(
                    Chat.replace(File.getMessage().getString("user.not_enough_level"), "{name}", getSkillName(skill))
                    , "{req_level}", Number.formatFull(reqLevel)));
        }
    }
}
