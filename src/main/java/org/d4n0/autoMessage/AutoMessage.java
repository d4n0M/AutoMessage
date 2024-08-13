package org.d4n0.autoMessage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class AutoMessage extends JavaPlugin {
    private static AutoMessage instance;
    private static int messageIndex = 0;
    private static List<String> messages;
    public static FileConfiguration config;
    private static int taskId;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        Objects.requireNonNull(getCommand("automessage")).setExecutor(new automessage());

        saveDefaultConfig(); // Save default config if it doesn't exist
        config = getConfig();
        messages = config.getStringList("Messages");

        if (!messages.isEmpty()) {
            startMessageCycle();
        } else {
            getServer().getLogger().warning("Invalid configuration. Please check the DelayBetween and Messages settings.");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static FileConfiguration getPluginConfig() {
        return config;
    }

    public static AutoMessage getInstance() {
        return instance;
    }

    public static List<String> getMessages() {
        return messages;
    }

    public static void setMessages(List<String> newMessages) {
        messages = newMessages;
        config.set("Messages", newMessages);
    }

    public static boolean removeMessage(String message) {
        boolean removed = messages.removeIf(m -> m.equalsIgnoreCase(message));
        if (removed) {
            config.set("Messages", messages);
            savePluginConfig();
        }
        return removed;
    }

    public static void savePluginConfig() {
        instance.saveConfig();
    }

    private void startMessageCycle() {
        int delay = config.getInt("DelayBetween") * 20;
        taskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                ArrayList<Player> players = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
                if(!players.isEmpty()) {
                    if (messageIndex < messages.size()){
                        String message = messages.get(messageIndex);
                        for (Player player : players) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        }
                        // Update the message index to the next message, looping back to the start if necessary
                        messageIndex = (messageIndex + 1) % messages.size();
                    } else{
                        messageIndex = 0;
                    }
                }
            }
        }, 0, delay);
    }

    public static void restartMessageCycle() {
        Bukkit.getServer().getScheduler().cancelTask(taskId);
        messageIndex = 0;
        instance.startMessageCycle();
    }
}