package com.zennyel.sao.commands;

import com.zennyel.sao.Z_Guilds;
import com.zennyel.sao.config.MessagesConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    private Z_Guilds plugin;
    private MessagesConfig messages;

    public ReloadCommand(Z_Guilds plugin, MessagesConfig messages) {
        this.plugin = plugin;
        this.messages = messages;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.reloadConfig();
        sender.sendMessage(messages.getMessage("reload-message"));
        return false;
    }
}
