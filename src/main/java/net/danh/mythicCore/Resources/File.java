package net.danh.mythicCore.Resources;

import net.danh.mythicCore.PlayerData.ExpData;
import net.xconfig.bukkit.model.SimpleConfigurationManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;

public class File {

    public static FileConfiguration getConfig() {
        return SimpleConfigurationManager.get().get("config.yml");
    }

    public static FileConfiguration getMessage() {
        return SimpleConfigurationManager.get().get("message.yml");
    }

    public static void createFiles() {
        SimpleConfigurationManager.get().build("", false, "config.yml", "message.yml", "Class/ex-class-file.yml", "PlayerData/example_data.yml");
    }

    public static void reloadFile() {
        SimpleConfigurationManager.get().reload("config.yml", "message.yml", "Class/ex-class-file.yml", "PlayerData/example_data.yml");
        try {
            ExpData.loadExpSource();
            ExpData.loadClass();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveFile() {
        SimpleConfigurationManager.get().save("config.yml", "message.yml", "Class/ex-class-file.yml", "PlayerData/example_data.yml");
    }
}
