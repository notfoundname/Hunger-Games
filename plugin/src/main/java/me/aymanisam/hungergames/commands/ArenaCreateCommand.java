package me.aymanisam.hungergames.commands;

import me.aymanisam.hungergames.HungerGames;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;

public class ArenaCreateCommand implements CommandExecutor {
    private final HungerGames plugin;
    private FileConfiguration arenaConfig = null;
    private File arenaFile = null;


    public ArenaCreateCommand(HungerGames plugin) {
        this.plugin = plugin;
        createArenaConfig();
    }

    public void createArenaConfig() {
        arenaFile = new File(plugin.getDataFolder(), "arena.yml");
        if (!arenaFile.exists()) {
            arenaFile.getParentFile().mkdirs();
            plugin.saveResource("arena.yml", false);
        }

        arenaConfig = YamlConfiguration.loadConfiguration(arenaFile);
    }

    public FileConfiguration getArenaConfig() {
        if (arenaConfig == null) {
            createArenaConfig();
        }
        return arenaConfig;
    }

    public void saveArenaConfig() {
        try {
            getArenaConfig().save(arenaFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.WARNING, plugin.getMessage("arena.save-error") + arenaFile, e);
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player player) {
            plugin.loadLanguageConfig(player);
            if (player.hasPermission("hungergames.create")) {
                if (player.hasMetadata("arena_pos1") && player.hasMetadata("arena_pos2")) {
                    Location pos1 = (Location) player.getMetadata("arena_pos1").get(0).value();
                    Location pos2 = (Location) player.getMetadata("arena_pos2").get(0).value();
                    if (pos1 != null && pos2 != null) {
                        getArenaConfig().set("region.world", Objects.requireNonNull(pos1.getWorld()).getName());
                        getArenaConfig().set("region.pos1.x", pos1.getX());
                        getArenaConfig().set("region.pos1.y", pos1.getY());
                        getArenaConfig().set("region.pos1.z", pos1.getZ());
                        getArenaConfig().set("region.pos2.x", pos2.getX());
                        getArenaConfig().set("region.pos2.y", pos2.getY());
                        getArenaConfig().set("region.pos2.z", pos2.getZ());
                        saveArenaConfig();
                        sender.sendMessage(plugin.getMessage("arena.region-created"));
                    } else {
                        sender.sendMessage(plugin.getMessage("arena.invalid-values"));
                    }
                } else {
                    sender.sendMessage(plugin.getMessage("arena.no-values"));
                }
            } else {
                sender.sendMessage(plugin.getMessage("no-permission"));
            }
        } else {
            sender.sendMessage(plugin.getMessage("no-server"));
        }
        return true;
    }
}
