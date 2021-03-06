package com.voxelboxstudios.finale.listener;

import com.voxelboxstudios.finale.MTP;
import com.voxelboxstudios.finale.state.GameState;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class ListenerDamage implements Listener {

    /** Damage **/

    @SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
    public void onDamage(EntityDamageEvent e) {
        /** Check state **/

        if(MTP.getState() == GameState.WAITING || MTP.getState() == GameState.LOBBY) {
            e.setCancelled(true);
        }
        
        
        /** Spectator **/
        
        if(e.getEntity() instanceof Player) {
        	/** Player **/
        	
        	Player p = (Player) e.getEntity();
        	
        	if(MTP.getSpectators().contains(p.getName())) e.setCancelled(true);
        	
        	
        	/** Blood **/
        	
        	if(!e.isCancelled()) {
        		p.getWorld().playEffect(p.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK.getId());
        	}
        }
    }

}
