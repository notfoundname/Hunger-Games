package me.aymanisam.hungergames.commands;

import me.aymanisam.hungergames.HungerGames;
import me.aymanisam.hungergames.handlers.ArenaHandler;
import me.aymanisam.hungergames.handlers.LangHandler;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class ArenaCreateCommand implements CommandExecutor {
    private final HungerGames plugin;
    private final LangHandler langHandler;
    private final ArenaHandler arenaHandler;

    public ArenaCreateCommand(HungerGames plugin) {
        this.plugin = plugin;
        this.langHandler = new LangHandler(plugin);
        this.arenaHandler = new ArenaHandler(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(langHandler.getMessage("no-server"));
            return true;
        }

        langHandler.loadLanguageConfig(player);

        if (!(player.hasPermission("hungergames.create"))) {
            sender.sendMessage(langHandler.getMessage("no-permission"));
            return true;
        }

        if (!(player.hasMetadata("arena_pos1") && player.hasMetadata("arena_pos2"))) {
            sender.sendMessage(langHandler.getMessage("arena.no-values"));
            return true;
        }

        Location pos1 = (Location) player.getMetadata("arena_pos1").get(0).value();
        Location pos2 = (Location) player.getMetadata("arena_pos2").get(0).value();

        if (pos1 == null || pos2 == null) {
            sender.sendMessage(langHandler.getMessage("arena.no-values"));
            return true;
        }

        arenaHandler.getArenaConfig().set("region.world", Objects.requireNonNull(pos1.getWorld()).getName());
        arenaHandler.getArenaConfig().set("region.pos1.x", pos1.getX());
        arenaHandler.getArenaConfig().set("region.pos1.y", pos1.getY());
        arenaHandler.getArenaConfig().set("region.pos1.z", pos1.getZ());
        arenaHandler.getArenaConfig().set("region.pos2.x", pos2.getX());
        arenaHandler.getArenaConfig().set("region.pos2.y", pos2.getY());
        arenaHandler.getArenaConfig().set("region.pos2.z", pos2.getZ());
        arenaHandler.saveArenaConfig();
        sender.sendMessage(langHandler.getMessage("arena.region-created"));

        return true;
    }
}
