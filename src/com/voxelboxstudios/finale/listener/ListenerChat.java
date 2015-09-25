package com.voxelboxstudios.finale.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ListenerChat implements Listener {

    /** Chat **/

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.setFormat("§6%s §e» §7%s");
    }

}
