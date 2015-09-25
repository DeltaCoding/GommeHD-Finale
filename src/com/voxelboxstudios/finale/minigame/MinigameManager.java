package com.voxelboxstudios.finale.minigame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.voxelboxstudios.finale.MTP;
import com.voxelboxstudios.finale.state.GameState;

public class MinigameManager {

	/** Minigames **/

	private List<Minigame> minigames = new ArrayList<Minigame>();


	/** Index **/

	private int index = 0;


	/** Setup **/

	public void setup() {
		/** Add minigames **/

		minigames.add(new MinigamePest());
		minigames.add(new MinigameBomb());
		minigames.add(new MinigameTargetShooting());


		/** Shuffle **/

		Collections.shuffle(minigames);
	}


	/** Random **/

	public void next() {
		/** Minigame **/

		Minigame m = minigames.get(index);


		/** Index **/

		index++;

		if(index == minigames.size()) index = 0;


		/** Start **/

		start(m);
	}


	/** Start **/
	
	public void start(final Minigame minigame) {
		/** Broadcast **/
		
		new BukkitRunnable() {
			private String current = minigame.getName();
			private int pos = 0;
			
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()) {
					/** Play sound **/
					
					p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1f, 1f);
					
					
					/** Clear chat **/
					
					for(int i = 0; i < 100; i++) p.sendMessage(" ");
					
					
					/** Send message **/
					
					p.sendMessage(MTP.PREFIX + "Gespielt wird:  §e" + current.substring(0, pos));
				}
				
				
				/** Position **/
				
				pos++;
				
				
				/** Check length **/
				
				if(pos > current.length()) {
					for(Player p : Bukkit.getOnlinePlayers()) {
						/** Play sound **/
						
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1f, 1f);
						
						
						/** Clear chat **/
						
						for(int i = 0; i < 100; i++) p.sendMessage(" ");	
					}
					
					
					/** Broadcast **/

					Bukkit.broadcastMessage("§7Das gespielte Spiel lautet: §e" + current);
					Bukkit.broadcastMessage("§7Beschreibung: §e" + minigame.getDescription());

					
					/** Scheduler **/
					
					new BukkitRunnable() {
						public void run() {
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


							/** Teleport **/

							for(Player p : attenders) {
								p.teleport(minigame.getLocation());
							}
							
							
							/** Start **/
							
							minigame.start(attenders);
						}
					}.runTaskLater(MTP.getPlugin(), 4 * 20L);
					
					
					/** Cancel task **/
					
					cancel();
				}
			}
		}.runTaskTimer(MTP.getPlugin(), 0L, 5L);
	}

	
	/** End **/
	
	public void end() {
		/** Set state **/

		MTP.setState(GameState.WAITING);


		/** Set minigame **/

		MTP.setMinigame(null);


		/** Teleport **/

		for (Player p : Bukkit.getOnlinePlayers()) {
			p.teleport(MTP.getGameSpawn());
		}


		/** Princess **/

		new BukkitRunnable() {
			public void run() {
				/** Let the princess speak **/

				MTP.getPrincess().speak();


				/** Play sound **/

				for (Player p : Bukkit.getOnlinePlayers()) {
					p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1, 3);
				}
			}
		}.runTaskLater(MTP.getPlugin(), 2 * 20L);
	}
}