package com.voxelboxstudios.finale.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;

import com.voxelboxstudios.finale.MTP;
import com.voxelboxstudios.finale.state.GameState;

public class ListenerJoin implements Listener {

	/** Join **/
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		/** Join message **/
		
		e.setJoinMessage(null);
		
		
		/** Player **/
		
		final Player p = e.getPlayer();
		
		
		/** Default properties **/
		
		p.setGameMode(GameMode.ADVENTURE);
		
		p.setFoodLevel(20);
		p.setHealth(20.0D);
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);

		p.setLevel(0);
		p.setExp(0);

		for(PotionEffect pe : p.getActivePotionEffects())  {
			p.removePotionEffect(pe.getType());
		}
		
		
		/** Spectator **/
		
		if(MTP.getState() != GameState.LOBBY) {
			/** Add to spectators **/
			
			if(!MTP.getSpectators().contains(p.getName())) {
				MTP.getSpectators().add(p.getName());
			}
			
			
			/** Set gamemode **/
			
			p.setGameMode(GameMode.SPECTATOR);
			
			
			/** Send message **/
			
			p.sendMessage(MTP.PREFIX + "Du bist Zuschauer.");
			
			
			/** Teleport **/
			
			p.teleport(MTP.getGameSpawn());
		} else {
			/** Teleport **/
			
			p.teleport(MTP.getLobbySpawn());
		}
	}
	
}
