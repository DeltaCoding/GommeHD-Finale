package com.voxelboxstudios.finale.state;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.voxelboxstudios.finale.MTP;

public class LobbyState {

	/** Constructors **/
	
	public LobbyState(final int time) {
		/** Timer **/
		
		new BukkitRunnable() {
			int currenttime = time;
			
			public void run() {
				/** Player **/
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					/** Current time **/
					
					p.setLevel(currenttime);
					
					
					/** Play sound **/
					
					if(currenttime % 5 == 0) p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1, 1);
					if(currenttime < 5) p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);
					
					
					/** Send message **/
					
					if(currenttime != 0 && (currenttime % 15 == 0 || currenttime <= 5)) {
						/** Send message **/
						
						if(currenttime == 1)
							p.sendMessage(MTP.PREFIX + "Das Spiel beginnt in §eeiner Sekunde.");
						else
							p.sendMessage(MTP.PREFIX + "Das Spiel beginnt in §e" + currenttime + " Sekunden§7.");
					}
					
					
					/** Set exp level **/
					
					p.setExp((1f / time) * currenttime);
				}
				
				
				/** Current time **/
				
				currenttime--;
				
				
				/** Check time **/
				
				if(currenttime == -1) {
					if(Bukkit.getOnlinePlayers().size() >= MTP.getMinPlayers()) {
						/** Loop **/
						
						for(Player p : Bukkit.getOnlinePlayers()) {
							p.setLevel(0);
							p.setExp(0f);
						}
						
						
						/** Ingame state **/
						
						new IngameState();
						
						
						/** Cancel **/
						
						cancel();
					} else {
						/** Broadcast **/
						
						if(MTP.getMinPlayers() - Bukkit.getOnlinePlayers().size() == 1)
							Bukkit.broadcastMessage(MTP.PREFIX + "Es fehlt noch §e" + (MTP.getMinPlayers() - Bukkit.getOnlinePlayers().size()) + " Spieler§7!" );
						else
							Bukkit.broadcastMessage(MTP.PREFIX + "Es fehlen noch §e" + (MTP.getMinPlayers() - Bukkit.getOnlinePlayers().size()) + " Spieler§7!" );
						
						
						/** Reset time **/
						
						currenttime = time;
					}
				}
			}
		}.runTaskTimer(MTP.getPlugin(), 0L, 20L);
	}
}