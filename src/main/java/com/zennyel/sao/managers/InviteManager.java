package com.zennyel.sao.managers;

import com.zennyel.sao.events.invite.PlayerInviteEvent;
import com.zennyel.sao.objects.guild.Guild;
import com.zennyel.sao.objects.Invite;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class InviteManager {

    private HashMap<Player, Invite> inviteHashMap;
    private PlayerGuildManager playerGuildManager;

    public InviteManager(PlayerGuildManager playerGuildManager) {
        this.playerGuildManager = playerGuildManager;
        this.inviteHashMap = new HashMap<>();
    }

    public void acceptInvite(Player player, Guild guild){
        playerGuildManager.setPlayerGuild(player, getInvite(player).getGuildInvited());
        inviteHashMap.remove(player);
    }

    public Invite getInvite(Player player){
        return inviteHashMap.get(player);
    }

    public boolean removeInvite(Player player){
        inviteHashMap.remove(player);
        return true;
    }

    public boolean invite(Player inviter, Player invitee, Invite invite){
        inviteHashMap.put(invitee, invite);
        Bukkit.getPluginManager().callEvent(new PlayerInviteEvent(invitee, playerGuildManager.getPlayerGuild(inviter)));
        return true;
    }

}
