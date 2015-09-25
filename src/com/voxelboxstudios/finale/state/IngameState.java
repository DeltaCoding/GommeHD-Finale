package com.voxelboxstudios.finale.state;

import org.bukkit.*;

import com.voxelboxstudios.finale.MTP;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class IngameState {

	/** Constructor **/
	
	public IngameState() {
		/** Set state **/
		
		MTP.setState(GameState.WAITING);
		
		
		/** Broadcast **/
		
		Bukkit.broadcastMessage(MTP.PREFIX + "Das Spiel beginnt!");


		/** Random **/

		Random r = new Random();

		
		/** Teleport **/

		for(Player p : Bukkit.getOnlinePlayers()) {
			/** Clear inventory **/

			p.getInventory().clear();


			/** Teleport **/

			p.teleport(MTP.getGameSpawn().clone().add(r.nextInt(3), 0, r.nextInt(3)));


			/** Gamemode **/

			p.setGameMode(GameMode.ADVENTURE);


			/** Play music **/

			p.playEffect(p.getLocation(), Effect.RECORD_PLAY, Material.RECORD_6.getId());
		}


		/** Scheduler **/

		new BukkitRunnable() {
			public void run() {
				/** Speak **/

				MTP.getPrincess().speak("Wer hält um meine Hand an? Tragt die Entscheidung zum Gatten in spannenden Kämpfen aus. Mögen die Spiele beginnen!");


				/** Play sound **/

				for(Player p : Bukkit.getOnlinePlayers()) {
					p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1, 3);
				}


				/** Scheduler **/

				new BukkitRunnable() {
					public void run() {
						/** Random minigame **/

						MTP.getMinigameManager().next();
					}
				}.runTaskLater(MTP.getPlugin(), 5 * 20L);
			}
		}.runTaskLater(MTP.getPlugin(), 2 * 20L);
	}
	
}
