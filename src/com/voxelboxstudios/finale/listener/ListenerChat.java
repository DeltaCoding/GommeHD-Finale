package com.voxelboxstudios.finale.listener;

import com.voxelboxstudios.finale.MTP;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ListenerChat implements Listener {

    /** Chat **/

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if(MTP.getSpectators().contains(e.getPlayer().getName()))
            e.setFormat("§7§o[Zuschauer] §6%s §e» §7%s");
        else
            e.setFormat("§6%s §e» §7%s");
    }

}
