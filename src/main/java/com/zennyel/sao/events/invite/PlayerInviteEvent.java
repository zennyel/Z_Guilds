package com.zennyel.sao.events.invite;

import com.zennyel.sao.events.CustomEvent;
import com.zennyel.sao.objects.guild.Guild;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PlayerInviteEvent extends CustomEvent {

    private Player invitee;
    private Guild guild;

    public PlayerInviteEvent(Player invitee, Guild guild) {
        this.invitee = invitee;
        this.guild = guild;
    }

    @Override
    public HandlerList getHandlers() {
        return super.getHandlers();
    }

    @Override
    public boolean isCancelled() {
        return super.isCancelled();
    }

    @Override
    public void setCancelled(boolean cancel) {
        super.setCancelled(cancel);
    }

    @Override
    public boolean callEvent() {
        return super.callEvent();
    }

    @Override
    public String getEventName() {
        return super.getEventName();
    }

    public Guild getGuild() {
        return guild;
    }

    public Player getInvitee() {
        return invitee;
    }
}
