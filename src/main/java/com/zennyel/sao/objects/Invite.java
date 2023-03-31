package com.zennyel.sao.objects;

import com.zennyel.sao.objects.guild.Guild;
import org.bukkit.entity.Player;

public class Invite {

    private Player inviter;
    private Player invitee;
    private Guild guildInvited;
    private boolean invited;

    public Invite(Player whoInvited, Player invitee, Guild guildInvited, boolean invited) {
        this.inviter = whoInvited;
        this.invitee = invitee;
        this.guildInvited = guildInvited;
        this.invited = invited;
    }

    public Player getWhoInvited() {
        return inviter;
    }

    public Player getInvitee() {
        return invitee;
    }

    public Guild getGuildInvited() {
        return guildInvited;
    }

    public boolean isInvited() {
        return invited;
    }
    
}
