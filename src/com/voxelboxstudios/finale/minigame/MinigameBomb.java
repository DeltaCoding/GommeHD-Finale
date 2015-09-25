package com.voxelboxstudios.finale.minigame;

import java.util.List;

import org.bukkit.entity.Player;

public class MinigameBomb extends Minigame {

	/** Get name **/
	
	@Override
	public String getName() { 
		return "Explosiv";
	}


	/** Get description **/

	@Override
	public String getDescription() {
		return "Gebe die Bombe bevor sie explodiert an einen anderen Spieler ab!";
	}

	
	/** Start **/
	
	@Override
	public void start(List<Player> attenders) {
		
	}

}
