package com.voxelboxstudios.finale.minigame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.voxelboxstudios.finale.MTP;
import com.voxelboxstudios.finale.scoreboards.Scoreboards;
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
		minigames.add(new MinigameLanzentunier());
		minigames.add(new MinigameBomb());
		minigames.add(new MinigameCastleWar());
		minigames.add(new MinigameTargetShooting());
		minigames.add(new MinigameLanzenschubsen());
		minigames.add(new MinigameHuegelkoenig());
		

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
					
					p.sendMessage(MTP.PREFIX + "Gespielt wird: §e" + current.substring(0, pos));
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
						@SuppressWarnings("deprecation")
						public void run() {
							/** Set state **/
							
							MTP.setState(GameState.MINIGAME);
							
							
							/** Set minigame **/
							
							MTP.setMinigame(minigame);
							
							
							/** Attenders **/
							
							List<Player> attenders = new ArrayList<Player>();
							
							for(Player p : Bukkit.getOnlinePlayers()) {
								if(!MTP.getSpectators().contains(p.getName())) {
									/** Stop effect **/
									
									p.playEffect(MTP.getGameSpawn(), Effect.RECORD_PLAY, 0);
									
									
									/** Add to attenders **/
									
									attenders.add(p);
								} else {
									p.teleport(minigame.getSpectatorLocation());
								}
							}


							/** Teleport **/

							for(Player p : attenders) {
								/** Default properties **/
								
								p.getInventory().clear();
								p.getInventory().setArmorContents(null);
								
								
								/** Teleport **/
								
								if(minigame.getLocation() != null) p.teleport(minigame.getLocation());
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
	
	@SuppressWarnings("deprecation")
	public void end() {
		/** Set state **/

		MTP.setState(GameState.WAITING);


		/** Set minigame **/

		MTP.setMinigame(null);


		/** Teleport **/

		Random r = new Random();
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.setExp(0f);
			p.setLevel(0);
			
			p.setHealth(20.0D);
			
			p.teleport(MTP.getGameSpawn().clone().add(r.nextInt(6) - 3, 0, r.nextInt(6) - 3));
			
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			
			if(!MTP.getSpectators().contains(p.getName()))
				p.setGameMode(GameMode.ADVENTURE);
			else
				p.setGameMode(GameMode.SPECTATOR);

			
			p.playEffect(MTP.getGameSpawn(), Effect.RECORD_PLAY, Material.RECORD_6.getId());
		}
		
		
		/** Scoreboards **/
		
		Scoreboards.update();


		/** Princess **/

		new BukkitRunnable() {
			public void run() {
				/** Let the princess speak **/

				MTP.getPrincess().speak();
				
				
				/** Heart **/
				
				MTP.getPrincessSpawn().getWorld().playEffect(MTP.getPrincessSpawn().clone().add(0, 1.5, 0), Effect.HEART, 1);


				/** Play sound **/

				for(Player p : Bukkit.getOnlinePlayers()) {
					p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1, 3);
				}
				
				
				/** Runnable **/
				
				new BukkitRunnable() {
					public void run() {
						next();
					}
				}.runTaskLater(MTP.getPlugin(), 15 * 20L);
			}
		}.runTaskLater(MTP.getPlugin(), 2 * 20L);
	}
}