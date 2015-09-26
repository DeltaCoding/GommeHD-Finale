package com.voxelboxstudios.finale;

import java.util.ArrayList;
import java.util.List;

import com.voxelboxstudios.finale.princess.Princess;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.voxelboxstudios.finale.guard.Guard;
import com.voxelboxstudios.finale.listener.*;
import com.voxelboxstudios.finale.minigame.Minigame;
import com.voxelboxstudios.finale.minigame.MinigameManager;
import com.voxelboxstudios.finale.state.GameState;
import com.voxelboxstudios.finale.state.LobbyState;

public class MTP extends JavaPlugin {

	/** Prefix **/
	
	public static final String PREFIX = ChatColor.GOLD + "Marry the Princess " + ChatColor.YELLOW + "» " + ChatColor.GRAY;
	

	/** Princess **/

	private static Princess princess;


	/** Game state **/
	
	private static GameState state;
	
	
	/** Plugin **/
	
	private static MTP plugin;
	
	
	/** Minigame **/
	
	private static Minigame minigame;
	
	
	/** Minigame manager **/
	
	private static MinigameManager manager;
	
	
	/** Spectators **/
	
	private static List<String> spectators;
	
	
	/** Guards **/
	
	private static List<Guard> guards;
	
	
	/** Locations **/
	
	private static Location gamespawn;
	private static Location lobbyspawn;
	private static Location princessspawn;
	
	/** Enable **/
	
	public void onEnable() {
		/** Plugin **/
		
		plugin = this;
		
		
		/** Guards **/
		
		guards = new ArrayList<Guard>();
		
		
		/** State **/
		
		state = GameState.LOBBY;
		
		
		/** Minigame manager **/
		
		manager = new MinigameManager();

		manager.setup();

		
		/** Locations **/
		
		gamespawn = new Location(Bukkit.getWorlds().get(0), 1551, 11, 376, 90, -30);
		lobbyspawn = new Location(Bukkit.getWorlds().get(0), 1571, 10, 387);
		princessspawn = new Location(Bukkit.getWorlds().get(0), 1542.5, 15, 376.5, -90, 38);
		
		
		/** Princess **/

		princess = new Princess(princessspawn);
		
		
		/** Spectators **/
		
		spectators = new ArrayList<String>();


		/** Worlds **/

		for(World w : Bukkit.getWorlds()) {
			/** Game rule **/

			w.setGameRuleValue("doDaylightCycle", "false");


			/** Set time **/

			w.setTime(12300L);


			/** Set thundering **/

			w.setThundering(false);


			/** Set storm **/

			w.setStorm(false);
		}
		
		
		/** Spawn guards **/
		
		new Guard(new Location(Bukkit.getWorlds().get(0), 1537.5, 11, 387.5, -90, 0));
		new Guard(new Location(Bukkit.getWorlds().get(0), 1537.5, 11, 383.5, -90, 0));
		new Guard(new Location(Bukkit.getWorlds().get(0), 1537.5, 11, 369.5, -90, 0));
		new Guard(new Location(Bukkit.getWorlds().get(0), 1537.5, 11, 365.5, -90, 0));
		

		/** Listeners **/
		
		PluginManager pm = Bukkit.getPluginManager();
		
		pm.registerEvents(new ListenerJoin(), plugin);
		pm.registerEvents(new ListenerLeave(), plugin);
		pm.registerEvents(new ListenerFood(), plugin);
		pm.registerEvents(new ListenerChat(), plugin);
		pm.registerEvents(new ListenerWeather(), plugin);
		pm.registerEvents(new ListenerDamage(), plugin);
		pm.registerEvents(new ListenerDrop(), plugin);
		pm.registerEvents(new ListenerMove(), plugin);
		pm.registerEvents(new ListenerArmorStand(), plugin);
		
		
		/** Lobby state **/
		
		new LobbyState(30);
	}
	
	
	/** Disable **/
	
	public void onDisable() {
		/** Remove guards **/
		
		for(Guard g : guards) {
			g.getArmorStand().remove();
		}
		
		
		/** Remove princess **/
		
		princess.getArmorStand().remove();
	}

	
	/** Get guards **/
	
	public static List<Guard> getGuards() {
		return guards;
	}
	
		
	/** Get lobby spawn **/
	
	public static Location getLobbySpawn() {
		return lobbyspawn;
	}
	
	
	/** Get game spawn **/
	
	public static Location getGameSpawn() {
		return gamespawn;
	}
	
	
	/** Get Princess Lobby Spawn **/
	
	public static Location getPrincessSpawn() {
		return princessspawn;
	}
	
	/** Get spectators **/
	
	public static List<String> getSpectators() {
		return spectators;
	}
	
	
	/** Get manager **/
	
	public static MinigameManager getMinigameManager() {
		return manager;
	}


	/** Get princess **/

	public static Princess getPrincess() {
		return princess;
	}


	/** Get minigame **/
	
	public static Minigame getMinigame() {
		return minigame;
	}
	
	
	/** Set minigame **/
	
	public static void setMinigame(Minigame newminigame) {
		minigame = newminigame;
	}
	
	
	/** Get plugin **/
	
	public static MTP getPlugin() {
		return plugin;
	}
	
	
	/** Get state **/
	
	public static GameState getState() {
		return state;
	}
	
	
	/** Set state **/
	
	public static void setState(GameState newstate) {
		state = newstate;
	}


	/** Minimal players **/
	
	public static int getMinPlayers() {
		return 2;
	}
}