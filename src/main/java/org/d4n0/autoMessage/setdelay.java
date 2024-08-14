package org.d4n0.autoMessage;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class setdelay implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        FileConfiguration config = AutoMessage.getPluginConfig();
        Player player;

        try {
            if (commandSender instanceof Player) {
                player = (Player) commandSender;
            } else {
                return true;
            }
        }catch (Exception e){
            return true;
        }

        if (!player.hasPermission("automessage.setdelay")) {
            player.sendMessage(ChatColor.RED + "Nemáš práva na použitie tohto príkazu");
            return true;
        }

        if (strings == null || strings.length == 0) {
            player.sendMessage(ChatColor.RED + "Nezadal si argument");
            return true;
        }

        try{
            int delay = Integer.parseInt(strings[0]);
            config.set("DelayBetween", delay);
            AutoMessage.savePluginConfig();
            return true;
        }catch (Exception e){
            player.sendMessage(ChatColor.RED + "Nezadal si číslo");
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (!player.hasPermission("automessage.remove")){
                return null;
            }
        }
        if (args.length == 1) {
            return Arrays.asList("add", "remove", "restart");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
            return AutoMessage.getMessages();
        }
        return null;
    }
}
