package me.aymanisam.hungergames.commands;

import me.aymanisam.hungergames.HungerGames;
import me.aymanisam.hungergames.handler.SetSpawnHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class JoinGameCommand implements CommandExecutor {
    private final HungerGames plugin;
    private final SetSpawnHandler setSpawnHandler;
    private final Set<Player> playersInGame;

    public JoinGameCommand(HungerGames plugin, SetSpawnHandler setSpawnHandler) {
        this.plugin = plugin;
        this.setSpawnHandler = setSpawnHandler;
        this.playersInGame = new HashSet<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getMessage("no-server"));
            return true;
        }

        plugin.loadLanguageConfig((Player) sender);
        if (playersInGame.contains(player)) {
            player.sendMessage(plugin.getMessage("join.already-joined"));
            return true;
        }

        if (setSpawnHandler.handleJoin(player)) {
            playersInGame.add(player);
        }
        return true;
    }
    public void addPlayerToGame(Player player) {
        playersInGame.add(player);
    }
    public void removePlayer(Player player) {
        playersInGame.remove(player);
    }
    public Set<Player> getPlayersInGame() {
        return playersInGame;
    }
}
