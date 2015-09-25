package com.voxelboxstudios.finale.state;

import org.bukkit.Bukkit;

import com.voxelboxstudios.finale.MTP;
import org.bukkit.entity.Player;

public class IngameState {











	/** Constructor **/
	
	public IngameState() {
		/** Set state **/
		
		MTP.setState(GameState.WAITING);
		
		
		/** Broadcast **/
		
		Bukkit.broadcastMessage(MTP.PREFIX + "Das Spiel beginnt!");
		
		
		/** Teleport **/

		for(Player p : Bukkit.getOnlinePlayers()) {
			/** Clear inventory **/

			p.getInventory().clear();


			/** Teleport **/

			p.teleport(MTP.getGameSpawn());
		}
	}
	
}
