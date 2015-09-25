package com.voxelboxstudios.finale.state;

import org.bukkit.Bukkit;

import com.voxelboxstudios.finale.MTP;

public class IngameState {

	/** Constructor **/
	
	public IngameState() {
		/** Set state **/
		
		MTP.setState(GameState.WAITING);
		
		
		/** Broadcast **/
		
		Bukkit.broadcastMessage(MTP.PREFIX + "Das Spiel beginnt!");
	}
	
}
