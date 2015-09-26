package com.voxelboxstudios.finale.minigame;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.voxelboxstudios.finale.MTP;

public abstract class Minigame implements Listener {
	
	/** Name **/

	public abstract String getName();
	
	
	/** Get description **/
	
	public abstract String getDescription();
	
	
	/** Get location **/
	
	public abstract Location getLocation();
	
	
	/** Get spectator location **/
	
	public abstract Location getSpectatorLocation();
	
	
	/** Start **/
	
	public abstract void startGame(List<Player> attenders);

	public void start(List<Player> attenders) {
		/** Listener **/

		Bukkit.getPluginManager().registerEvents(this, MTP.getPlugin());


		/** Start game **/

		startGame(attenders);
	}

	
	/** End **/
	
	public void end() {
		end(false, null);
	}
	
	public void end(boolean deathmatch, Player winner) {
		/** Listener **/

		HandlerList.unregisterAll(this);

		
		/** Clear inventories **/
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.setGameMode(GameMode.ADVENTURE);
			p.setHealth(20.0D);
		}
		
		
		/** End in minigame manager **/
		
		MTP.getMinigameManager().end(deathmatch, winner);
	}
	
}
