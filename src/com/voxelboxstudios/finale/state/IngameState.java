package com.voxelboxstudios.finale.state;

import org.bukkit.Bukkit;

import com.voxelboxstudios.finale.MTP;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
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
		}


		/** Scheduler **/

		new BukkitRunnable() {
			public void run() {

			}
		}.runTaskLater(MTP.getPlugin(), 2 * 20L);

		/** Speak **/

		MTP.getPrincess().speak();
	}
	
}
