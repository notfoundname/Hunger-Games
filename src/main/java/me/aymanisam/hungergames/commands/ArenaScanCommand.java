package me.aymanisam.hungergames.commands;

import me.aymanisam.hungergames.HungerGames;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ArenaScanCommand implements CommandExecutor {
    private final HungerGames plugin;

    public ArenaScanCommand(HungerGames plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}