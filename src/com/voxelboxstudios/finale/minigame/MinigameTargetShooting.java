package com.voxelboxstudios.finale.minigame;

import java.util.List;

import org.bukkit.entity.Player;

public class MinigameTargetShooting extends Minigame {

	/** Get name **/
	
	@Override
	public String getName() { 
		return "Zielschie�en";
	}


	/** Get description **/

	@Override
	public String getDescription() {
		return "Treffe m�glichst viele Ziele in einer angegebenen Zeit, um der Prinzessin deine Zielsicherheit zu beweisen!";
	}

	
	/** Start **/
	
	@Override
	public void start(List<Player> attenders) {
		
	}

}
