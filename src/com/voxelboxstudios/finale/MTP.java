package com.voxelboxstudios.finale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.voxelboxstudios.finale.princess.Princess;
import com.voxelboxstudios.finale.resource.PacketPlayResourcePackStatus;
import com.voxelboxstudios.finale.resource.Status;
import com.voxelboxstudios.finale.speakers.Guard;
import com.voxelboxstudios.finale.speakers.Hofnarr;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

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
	
	
	/** Hofnarren **/
	
	private static List<Hofnarr> hofnarren;
	
	
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
	
	
	/** Packet **/
	
	public static PacketPlayResourcePackStatus packet;
	
	
	/** Points **/
	
	public static Map<String, Integer> points = new HashMap<String, Integer>();
	
	
	/** Enable **/
	
	public void onEnable() {
		/** Plugin **/
		
		plugin = this;
		
		
		/** Guards **/
		
		guards = new ArrayList<Guard>();
		
		
		/** Hofnarren **/
		
		hofnarren = new ArrayList<Hofnarr>();
		
		
		/** Packet **/
		
		packet = new PacketPlayResourcePackStatus();
		
		
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
		
		new Guard(new Location(Bukkit.getWorlds().get(0), 1537.5, 11, 387.5, -90, 0), false);
		new Guard(new Location(Bukkit.getWorlds().get(0), 1537.5, 11, 383.5, -90, 0), false);
		new Guard(new Location(Bukkit.getWorlds().get(0), 1537.5, 11, 369.5, -90, 0), true);
		new Guard(new Location(Bukkit.getWorlds().get(0), 1537.5, 11, 365.5, -90, 0), false);
		
		
		/** Hofnarren **/
		
		new Hofnarr(new Location(Bukkit.getWorlds().get(0), 1541.5, 15, 375.5, -90, 35), "DerSpinner");
		new Hofnarr(new Location(Bukkit.getWorlds().get(0), 1555.5, 11, 382.5, 135, 0), "l_Maxi_l");
		new Hofnarr(new Location(Bukkit.getWorlds().get(0), 1646.5, 9, 392.5, 45, 0), "l_Maxi_l");
		

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
		pm.registerEvents(new ListenerInteract(), plugin);
		pm.registerEvents(new ListenerBuild(), plugin);
		pm.registerEvents(new ListenerClick(), plugin);
		pm.registerEvents(new ListenerProjectile(), plugin);
		pm.registerEvents(new ListenerLogin(), plugin);
		
		
		/** Lobby state **/
		
		new LobbyState(30);
	}
	
	
	/** Disable **/
	
	public void onDisable() {
		/** Remove guards **/
		
		for(Guard g : guards) {
			g.getArmorStand().remove();
		}
		
		
		/** Remove Hofnarren **/
		
		for(Hofnarr h : hofnarren) {
			h.getArmorStand().remove();
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

	
	/** Resource pack result **/
	
	public static void resourcePackResult(final Player p, Status status) {
		/** Check status **/
		
		if(!(status == Status.SUCCESSFULLY_LOADED || status == Status.ACCEPTED)) {
			/** Kick player **/
			
			new BukkitRunnable() {
				public void run() {
					p.kickPlayer("§cBitte aktiviere Resource Packs in deinen Einstellungen.");
				}
			}.runTask(plugin);
		}
	}


	/** Hofnarren **/
	
	public static List<Hofnarr> getHofnarren() {
		return hofnarren;
	}
}