package com.voxelboxstudios.finale.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerLeave implements Listener {

	/** Leave **/
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		/** Quit message **/
		
		e.setQuitMessage(null);
	}
	
	
	/** Kick **/
	
	@EventHandler
	public void onKick(PlayerKickEvent e) {
		/** Quit message **/
		
		e.setLeaveMessage(null);
	}	
	
}
