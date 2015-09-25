package com.voxelboxstudios.finale.minigame;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.voxelboxstudios.finale.MTP;
import com.voxelboxstudios.finale.state.GameState;

public class MinigameManager {

	/** Start **/
	
	public void start(Minigame minigame) {
		/** Set state **/
		
		MTP.setState(GameState.MINIGAME);
		
		
		/** Set minigame **/
		
		MTP.setMinigame(minigame);
		
		
		/** Attenders **/
		
		List<Player> attenders = new ArrayList<Player>();
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(!MTP.getSpectators().contains(p.getName())) {
				attenders.add(p);
			}
		}
		
		
		/** Start **/
		
		minigame.start(attenders);
	}

	
	/** End **/
	
	public void end() {
		/** Set state **/
		
		MTP.setState(GameState.WAITING);
		
		
		/** Set minigame **/
		
		MTP.setMinigame(null);
	}
	
}
