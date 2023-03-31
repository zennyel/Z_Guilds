package com.zennyel.sao.liesteners;

import com.zennyel.sao.config.MessagesConfig;
import com.zennyel.sao.events.invite.PlayerInviteEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerInviteListener implements Listener {
    MessagesConfig messages;

    public PlayerInviteListener(MessagesConfig messages) {
        this.messages = messages;
    }

    @EventHandler
    public void onPlayerInvite(PlayerInviteEvent event){
        Player p = event.getInvitee();
        p.sendMessage(messages.getMessage("invited-to-a-guild"));
    }

}
