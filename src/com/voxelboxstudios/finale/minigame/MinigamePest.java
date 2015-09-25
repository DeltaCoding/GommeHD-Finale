package com.voxelboxstudios.finale.minigame;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MinigamePest extends Minigame {

	/** Get name **/
	
	@Override
	public String getName() { 
		return "Pest";
	}


	/** Get description **/

	@Override
	public String getDescription() {
		return "Versuche der Pest aus dem Weg zu gehen indem du vor Infizierten wegrennst!";
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
