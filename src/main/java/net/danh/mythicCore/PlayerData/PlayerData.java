package net.danh.mythicCore.PlayerData;

import net.danh.mythicCore.MythicCore;
import net.xconfig.bukkit.model.SimpleConfigurationManager;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class PlayerData {

    public final Player p;
    public File playerdataFile;
    public FileConfiguration playerdata;

    public PlayerData(Player player) {
        p = player;
    }

    public void loadData() {
        PlayerData playerData = new PlayerData(p);
        createFileData();
        String currentClass = getFileData().getString("current");
        if (currentClass != null) {
            for (String classID : ExpData.getListClass()) {
                if (!currentClass.equalsIgnoreCase(classID)) {
                    playerData.setClass(classID);
                    playerData.setLevel(Math.max(1, getFileData().getInt("class." + classID + ".level")));
                    playerData.setExp(Math.max(0, getFileData().getInt("class." + classID + ".exp")));
                }
            }
            playerData.setClass(currentClass);
            playerData.setLevel(Math.max(1, getFileData().getInt("class." + currentClass + ".level")));
            playerData.setExp(Math.max(0, getFileData().getInt("class." + currentClass + ".exp")));
        }
    }

    public void saveData() {
        createFileData();
        getFileData().set("id", getPlayer().getName());
        getFileData().set("current", getClassID());
        PlayerData playerData = new PlayerData(p);
        for (String classID : ExpData.getListClass()) {
            playerData.setClass(classID);
            getFileData().set("class." + classID + ".level", playerData.getLevel());
            getFileData().set("class." + classID + ".exp", playerData.getExp());
        }
        saveFileData();
        reloadFileData();
    }

    private String getFileNameData() {
        return "PlayerData/" + p.getName() + "_" + p.getUniqueId() + ".yml";
    }

    public void createFileData() {
        playerdataFile = new File(MythicCore.getMythicCore().getDataFolder(), getFileNameData());
        if (!playerdataFile.exists()) SimpleConfigurationManager.get().build("", true, getFileNameData());
        playerdata = new YamlConfiguration();

        try {
            playerdata.load(playerdataFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getFileData() {
        return playerdata;
    }

    public void reloadFileData() {
        playerdata = YamlConfiguration.loadConfiguration(playerdataFile);
    }

    public void saveFileData() {
        try {
            playerdata.save(playerdataFile);
        } catch (IOException ignored) {
        }
    }

    public String getClassID() {
        AtomicReference<String> defaultClassID = new AtomicReference<>();
        ExpData.getListClass().forEach(classID -> {
            if (new ClassManager(classID).getDefault())
                defaultClassID.set(classID);
        });
        return ExpData.playerInfo.getOrDefault(p.getName() + ";class", defaultClassID.get());
    }

    public void setClass(String classID) {
        if (ExpData.playerInfo.containsKey(p.getName() + ";class")) {
            ExpData.playerInfo.replace(p.getName() + ";class", classID);
        } else {
            ExpData.playerInfo.put(p.getName() + ";class", classID);
        }
    }

    public String getClassName() {
        return new ClassManager(getClassID()).getClassName();
    }

    public int getLevel() {
        return ExpData.playerStats.getOrDefault(p.getName() + ";" + getClassID() + ";main_level", 1);
    }

    public void setLevel(int level) {
        if (ExpData.playerStats.containsKey(p.getName() + ";" + getClassID() + ";main_level")) {
            ExpData.playerStats.replace(p.getName() + ";" + getClassID() + ";main_level", level);
        } else {
            ExpData.playerStats.put(p.getName() + ";" + getClassID() + ";main_level", level);
        }
    }

    public void editLevel(int level) {
        int final_level = getLevel() + level;
        if (ExpData.playerStats.containsKey(p.getName() + ";" + getClassID() + ";main_level")) {
            if (final_level >= 1 && final_level <= new ClassManager(getClassID()).getMaxLevel()) {
                ExpData.playerStats.replace(p.getName() + ";" + getClassID() + ";main_level", final_level);
            } else
                ExpData.playerStats.replace(p.getName() + ";" + getClassID() + ";main_level", new ClassManager(getClassID()).getMaxLevel());
        } else {
            if (final_level >= 1 && final_level <= new ClassManager(getClassID()).getMaxLevel()) {
                ExpData.playerStats.put(p.getName() + ";" + getClassID() + ";main_level", final_level);
            } else
                ExpData.playerStats.put(p.getName() + ";" + getClassID() + ";main_level", new ClassManager(getClassID()).getMaxLevel());
        }
    }

    public int getExp() {
        return ExpData.playerStats.getOrDefault(p.getName() + ";" + getClassID() + ";main_exp", 0);
    }

    public void setExp(int exp) {
        if (ExpData.playerStats.containsKey(p.getName() + ";" + getClassID() + ";main_exp")) {
            ExpData.playerStats.replace(p.getName() + ";" + getClassID() + ";main_exp", exp);
        } else {
            ExpData.playerStats.put(p.getName() + ";" + getClassID() + ";main_exp", exp);
        }
    }

    public void editExp(int exp) {
        int final_exp = getExp() + exp;
        if (ExpData.playerStats.containsKey(p.getName() + ";" + getClassID() + ";main_exp")) {
            if (final_exp >= 1 && final_exp <= new ClassManager(getClassID()).getMaxExp(getLevel())) {
                ExpData.playerStats.replace(p.getName() + ";" + getClassID() + ";main_exp", final_exp);
            } else {
                int count_exp = final_exp - new ClassManager(getClassID()).getMaxExp(getLevel());
                editLevel(1);
                editExp(count_exp);
            }
        } else {
            if (final_exp >= 1 && final_exp <= new ClassManager(getClassID()).getMaxExp(getLevel())) {
                ExpData.playerStats.put(p.getName() + ";" + getClassID() + ";main_exp", final_exp);
            } else {
                int count_exp = final_exp - new ClassManager(getClassID()).getMaxExp(getLevel());
                editLevel(1);
                editExp(count_exp);
            }
        }
    }

    public void syncXPBar() {
        float xp = (float) getExp();
        float level = (float) getLevel();
        float req = new ClassManager(getClassID()).getMaxExp(getLevel());
        if (req == 0) req = new ClassManager(getClassID()).getMaxExp(getLevel());
        if (xp / req <= 1 && level >= 1) {
            p.setExp(Float.parseFloat(String.valueOf(xp / req)));
            p.setLevel((int) level);
        }
    }

    public Player getPlayer() {
        return p;
    }
}
