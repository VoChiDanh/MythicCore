package net.danh.mythicCore.PlayerData;

import net.danh.mythicCore.MythicCore;
import net.xconfig.bukkit.model.SimpleConfigurationManager;
import org.apache.commons.lang3.Validate;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class ExpData {
    private static final HashMap<String, List<Long>> expSource = new HashMap<>();
    private static final List<String> listClass = new ArrayList<>();
    public static HashMap<String, String> playerInfo = new HashMap<>();
    public static HashMap<String, Long> playerStats = new HashMap<>();

    public static void loadClass() throws IOException {
        if (!listClass.isEmpty())
            listClass.clear();
        File directory = new File(MythicCore.getMythicCore().getDataFolder(), "Class");
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    String className = file.getName().replace(".yml", "");
                    if (file.isFile()) {
                        listClass.add(className);
                        SimpleConfigurationManager.get().build("", false, "Class/" + className + ".yml");
                        MythicCore.getMythicCore().getLogger().log(Level.INFO, "Loaded class " + className);
                    }
                }
            } else {
                MythicCore.getMythicCore().getLogger().log(Level.INFO, "The directory is empty.");
            }
        } else {
            MythicCore.getMythicCore().getLogger().log(Level.INFO, "The specified path is not a directory or does not exist.");
        }
    }

    public static void loadExpSource() throws IOException {
        File directory = new File(MythicCore.getMythicCore().getDataFolder(), "EXPSource");
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && !file.getName().replace(".txt", "").equalsIgnoreCase("exp")) {
                        load(file.getName());
                    }
                }
            } else {
                MythicCore.getMythicCore().getLogger().log(Level.INFO, "The directory is empty.");
            }
        } else {
            MythicCore.getMythicCore().getLogger().log(Level.INFO, "The specified path is not a directory or does not exist.");
        }
    }

    public static void load(String expFile) throws IOException {
        getExpSource().remove(expFile);
        loadTextFile(expFile);

        File file = new File(MythicCore.getMythicCore().getDataFolder(), "EXPSource" + File.separator + expFile);
        if (!file.exists()) {
            MythicCore.getMythicCore().getLogger().severe("The expected file " + file.getPath() + " does not exist.");
            return; // Exit if the file does not exist
        }
        List<Long> expList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String readLine;
            while ((readLine = reader.readLine()) != null) {
                expList.add(Long.valueOf(readLine));
            }
        }
        getExpSource().put(expFile.replace(".txt", ""), expList);
        MythicCore.getMythicCore().getLogger().log(Level.INFO, "Loaded EXPSource: " + expFile.replace(".txt", ""));
        Validate.isTrue(!expSource.isEmpty(), "There must be at least one exp value in your exp curve");
    }

    private static void copyFileFromJar(String resourcePath, File destination) {
        try (InputStream in = MythicCore.getMythicCore().getResource(resourcePath)) {
            if (in == null) {
                MythicCore.getMythicCore().getLogger().severe("Default file " + resourcePath + " not found in JAR.");
                return;
            }

            // Ensure the destination parent directory exists
            File parentDir = destination.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs(); // Create the parent directories if they do not exist
            }

            // Copy the input stream to the destination file
            Files.copy(in, destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            MythicCore.getMythicCore().getLogger().info("Default file " + resourcePath + " copied to " + destination.getPath());
        } catch (IOException e) {
            MythicCore.getMythicCore().getLogger().severe("Error copying default file: " + e.getMessage());
        }
    }

    public static void loadTextFile(String expFile) {
        File dataFolder = MythicCore.getMythicCore().getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        File expSourceDir = new File(dataFolder, "EXPSource");
        if (!expSourceDir.exists()) {
            expSourceDir.mkdirs(); // Create the EXPSource directory if it doesn't exist
        }

        File file = new File(expSourceDir, expFile);
        if (!file.exists()) {
            MythicCore.getMythicCore().getLogger().info("File not found: " + expFile + ". Creating default file.");
            copyFileFromJar("EXPSource/" + expFile, file); // Ensure the path is correct
        }
    }

    public static long getExperience(String expSource, long level) {
        Validate.isTrue(level > 0, "Level must be stricly positive");
        return getExpSource().get(expSource).get(Math.toIntExact(Math.min(level, getExpSource().get(expSource).size()) - 1));
    }

    public static int getMaxLevel(String expSource) {
        return getExpSource().get(expSource).size() - 1;
    }

    public static HashMap<String, List<Long>> getExpSource() {
        return expSource;
    }

    public static List<String> getListClass() {
        return listClass;
    }
}
