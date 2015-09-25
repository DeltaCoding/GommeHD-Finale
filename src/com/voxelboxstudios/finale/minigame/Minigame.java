package com.voxelboxstudios.finale.minigame;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.voxelboxstudios.finale.MTP;

public abstract class Minigame implements Listener {

	/** Constructor **/
	
	public Minigame() {
		/** Register listener **/
		
		Bukkit.getPluginManager().registerEvents(this, MTP.getPlugin());
	}
	
	
	/** Name **/

	public abstract String getName();
	
	
	/** Get description **/
	
	public abstract String getDescription();
	
	
	/** Start **/
	
	public abstract void start(List<Player> attenders);
	
	
	/** End **/
	
	public void end() {
		/** End in minigame manager **/
		
		MTP.getMinigameManager().end();
	}
	
}
