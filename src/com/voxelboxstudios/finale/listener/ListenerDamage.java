package com.voxelboxstudios.finale.listener;

import com.voxelboxstudios.finale.MTP;
import com.voxelboxstudios.finale.state.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class ListenerDamage implements Listener {

    /** Damage **/

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        /** Check state **/

        if(MTP.getState() == GameState.WAITING || MTP.getState() == GameState.LOBBY) {
            e.setCancelled(true);
        }
    }

}
