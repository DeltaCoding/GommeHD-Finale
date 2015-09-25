package com.voxelboxstudios.finale.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ListenerJoin implements Listener {

	/** Join **/
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		/** Join message **/
		
		e.setJoinMessage(null);
	}
	
}
