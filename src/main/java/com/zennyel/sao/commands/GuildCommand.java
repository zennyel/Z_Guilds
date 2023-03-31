package com.zennyel.sao.commands;

import com.zennyel.sao.Z_Guilds;
import com.zennyel.sao.config.GuildConfig;
import com.zennyel.sao.config.MessagesConfig;
import com.zennyel.sao.managers.InviteManager;
import com.zennyel.sao.managers.PlayerGuildManager;
import com.zennyel.sao.managers.PluginManager;
import com.zennyel.sao.menu.GuildMainGUI;
import com.zennyel.sao.objects.guild.Guild;
import com.zennyel.sao.objects.Invite;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GuildCommand implements CommandExecutor {

    private Z_Guilds instance;
    private PluginManager manager;
    private PlayerGuildManager playerGuild;
    private MessagesConfig messages;
    private GuildConfig guildConfig;
    private InviteManager inviteManager;

    public GuildCommand(Z_Guilds instance) {
        this.instance = instance;
        this.manager = instance.getManager();
        this.messages = manager.getMessages();
        this.guildConfig = manager.getGuildConfig();
        this.playerGuild = manager.getPlayerGuildManager();
        this.inviteManager = manager.getInviteManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            return false;
        }
        Player player = ((Player) sender).getPlayer();

        if(!player.hasPermission("guild.use")){
            player.sendMessage();
            return false;
        }

        Guild guild = playerGuild.getPlayerGuild(player);

        if(args.length == 0){
            if(guild == null){
                player.sendMessage(messages.getMessage("not-in-a-guild"));
                return false;
            }
            Inventory mainMenu = Bukkit.createInventory(null, 27, "§6Guild Menu");
            new GuildMainGUI(mainMenu, null, player);
            return true;
        }

        switch (args[0]){
            case "criar":
                if(playerGuild.getPlayerGuild(player) != null){
                    player.sendMessage("already-in-guild");
                    return false;
                }

                if(args.length < 2){
                    player.sendMessage(messages.getMessage("wrong-create-command"));
                    return false;
                }

                if(args[1].length() > guildConfig.getConfiguration().getInt("Guild.maxNameLength")){
                    player.sendMessage(messages.getMessage("max-name-length"));
                    return false;
                }

                playerGuild.createGuild(args[1],player);
                player.sendMessage(messages.getMessage("guild-create-command"));
                return true;
            case "sair":
                if(playerGuild.getPlayerGuild(player) == null){
                    player.sendMessage(messages.getMessage("already-not-in-guild"));
                    return false;
                }

                playerGuild.setPlayerGuild(player, null);
                player.sendMessage(messages.getMessage("leaved-guild-message"));
                return true;
            case "convidar":

                if(args.length < 2){
                    player.sendMessage(messages.getMessage("wrong-invite-command"));
                    return false;
                }

                Player invitee = Bukkit.getPlayer(args [1]);

                if(!invitee.isOnline()){
                    player.sendMessage(messages.getMessage("invitee-not-online"));
                    return false;
                }

                inviteManager.invite(player, invitee, new Invite(player, invitee, guild, true));
                return true;
        }


            return false;
    }
}
