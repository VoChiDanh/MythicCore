package net.danh.mythicCore.CMD;

import net.danh.mythicCore.PlayerData.PlayerData;
import net.danh.mythicCore.Resources.File;
import net.danh.mythicCore.Utils.Chat;
import net.danh.mythicCore.Utils.Number;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MCCMD extends CMDBase {
    public MCCMD() {
        super("mcore");
    }

    @Override
    public void execute(@NotNull CommandSender c, String[] args) {
        if (c.hasPermission("mcore.admin")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    File.reloadFile();
                    Chat.sendMessage(c, File.getMessage().getString("admin.reload"));
                }
            } else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("level")) {
                    Player p = Bukkit.getPlayer(args[1]);
                    int number = Number.getInteger(args[2]);
                    PlayerData playerData = new PlayerData(p);
                    playerData.setLevel(number);
                } else if (args[0].equalsIgnoreCase("editLevel")) {
                    Player p = Bukkit.getPlayer(args[1]);
                    int number = Number.getInteger(args[2]);
                    PlayerData playerData = new PlayerData(p);
                    playerData.editLevel(number);
                } else if (args[0].equalsIgnoreCase("exp")) {
                    Player p = Bukkit.getPlayer(args[1]);
                    int number = Number.getInteger(args[2]);
                    PlayerData playerData = new PlayerData(p);
                    playerData.setExp(number);
                } else if (args[0].equalsIgnoreCase("editExp")) {
                    Player p = Bukkit.getPlayer(args[1]);
                    int number = Number.getInteger(args[2]);
                    PlayerData playerData = new PlayerData(p);
                    playerData.editExp(number);
                }
            }
        }
    }

    @Override
    public List<String> TabComplete(CommandSender sender, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();
        if (args.length == 1) {
            if (sender.hasPermission("mcore.admin")) {
                commands.add("reload");
                commands.add("level");
                commands.add("editLevel");
                commands.add("exp");
                commands.add("editExp");
            }
            StringUtil.copyPartialMatches(args[0], commands, completions);
        } else if (args.length == 2) {
            if (sender.hasPermission("mcore.admin")) {
                if (args[0].equalsIgnoreCase("level")
                        || args[0].equalsIgnoreCase("editLevel")
                        || args[0].equalsIgnoreCase("exp")
                        || args[0].equalsIgnoreCase("editExp")) {
                    Bukkit.getOnlinePlayers().forEach(player -> commands.add(player.getName()));
                }
            }
            StringUtil.copyPartialMatches(args[1], commands, completions);
        } else if (args.length == 3) {
            if (sender.hasPermission("mcore.admin")) {
                if (args[0].equalsIgnoreCase("level")
                        || args[0].equalsIgnoreCase("editLevel")
                        || args[0].equalsIgnoreCase("exp")
                        || args[0].equalsIgnoreCase("editExp")) {
                    for (int i = 0; i < 100; i += 10)
                        commands.add(String.valueOf(i));
                }
            }
            StringUtil.copyPartialMatches(args[2], commands, completions);
        }
        Collections.sort(completions);
        return completions;
    }
}
