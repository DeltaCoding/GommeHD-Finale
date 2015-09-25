package com.voxelboxstudios.finale.minigame;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MinigameTargetShooting extends Minigame {

	/** Get name **/
	
	@Override
	public String getName() { 
		return "Zielschießen";
	}


	/** Get description **/

	@Override
	public String getDescription() {
		return "Treffe möglichst viele Ziele in einer angegebenen Zeit, um der Prinzessin deine Zielsicherheit zu beweisen!";
	}

	
	/** Start **/
	
	@Override
	public void start(List<Player> attenders) {
		
	}
	
	
	/** Get location **/
	
	@Override
	public Location getLocation() {
		return new Location(Bukkit.getWorlds().get(0), 200, 32, 200);
	}
}
