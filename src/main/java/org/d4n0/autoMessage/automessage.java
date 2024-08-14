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

public class automessage implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        AutoMessage instance = AutoMessage.getInstance();
        FileConfiguration config = AutoMessage.getPluginConfig();
        List<String> messages = AutoMessage.getMessages();
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

        if (strings == null || strings.length == 0) {
            player.sendMessage(ChatColor.RED + "Nezadal si argument");
            return true;
        }

        if (strings[0].equalsIgnoreCase("restart") && player.hasPermission("automessage.restart")) {
            AutoMessage.restartMessageCycle();
            player.sendMessage(ChatColor.GREEN + "Cyklus správ bol reštartovaný");
            return true;
        }

        if (strings[0].equalsIgnoreCase("add") && player.hasPermission("automessage.add")) {
            if (strings.length < 2) {
                player.sendMessage(ChatColor.RED + "Málo argumentov");
                return true;
            }
            String message = String.join(" ", Arrays.copyOfRange(strings, 1, strings.length));
            messages.add(message);
            config.set("Messages", messages);
            AutoMessage.setMessages(messages);
            AutoMessage.savePluginConfig();
            player.sendMessage(ChatColor.GREEN + "Správa pridaná");
            return true;
        }

        if (strings[0].equalsIgnoreCase("remove") && player.hasPermission("automessage.remove")) {
            if (strings.length < 2) {
                return true;
            }
            String messageToRemove = String.join(" ", Arrays.copyOfRange(strings, 1, strings.length));
            if (AutoMessage.removeMessage(messageToRemove)) {
                player.sendMessage(ChatColor.GREEN + "Správa odstránená");
            } else {
                player.sendMessage(ChatColor.RED + "Správa nebola nájdená");
            }
            return true;
        } else if (!(strings[0].equalsIgnoreCase("remove") || strings[0].equalsIgnoreCase("restart") || strings[0].equalsIgnoreCase("add"))){
            player.sendMessage(ChatColor.RED + "Zlý argument");
        }else{
            player.sendMessage(ChatColor.RED + "Nemáš práva na použitie tohto príkazu");
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
