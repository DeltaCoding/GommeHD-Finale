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
	
	
	/** Secondary **/
	
	private static List<Princess> secondary;
	

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
		
		
		/** Princesses **/
		
		secondary = new ArrayList<Princess>();
		
		
		/** Packet **/
		
		packet = new PacketPlayResourcePackStatus();
		
		
		/** State **/
		
		state = GameState.LOBBY;
		
		
		/** Minigame manager **/
		
		manager = new MinigameManager();

		manager.setup();

		
		/** Locations **/
		
		gamespawn = new Location(Bukkit.getWorlds().get(0), 1551, 11, 376, 90, -30);
		lobbyspawn = new Location(Bukkit.getWorlds().get(0), 1749, 11, 431.5);
		princessspawn = new Location(Bukkit.getWorlds().get(0), 1542.5, 15, 376.5, -90, 38);
		
		
		/** Princess **/

		princess = new Princess(princessspawn);
		
		
		/** Spectators **/
		
		spectators = new ArrayList<String>();


		/** Worlds **/

		for(World w : Bukkit.getWorlds()) {
			/** Game rule **/

			w.setGameRuleValue("doDaylightCycle", "false");
			w.setGameRuleValue("doFireTick", "false");
			

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
		new Guard(new Location(Bukkit.getWorlds().get(0), 1632.5, 11, 396.5, -45, 0), false);
		new Guard(new Location(Bukkit.getWorlds().get(0), 1612.5, 27, 442, 135, 0), false);
		new Guard(new Location(Bukkit.getWorlds().get(0), 1571.5, 9, 563.5, 135, 0), false);
		new Guard(new Location(Bukkit.getWorlds().get(0), 1567.5, 9, 563.5, 135, 0), false);
		new Guard(new Location(Bukkit.getWorlds().get(0), 1738.5, 4, 425.5, -90, 0), false);
		
		
		/** Hofnarren **/
		
		new Hofnarr(new Location(Bukkit.getWorlds().get(0), 1541.5, 15, 375.5, -90, 35), "DerSpinner");
		new Hofnarr(new Location(Bukkit.getWorlds().get(0), 1555.5, 11, 376.5, 90, 0), "l_Maxi_l");
		new Hofnarr(new Location(Bukkit.getWorlds().get(0), 1646.5, 9, 392.5, 45, 0), "l_Maxi_l");
		new Hofnarr(new Location(Bukkit.getWorlds().get(0), 1749.5, 4, 441.5, -180, 0), "l_Maxi_l");
		
		
		/** Princesses **/
		
		new Princess(new Location(Bukkit.getWorlds().get(0), 1611.5, 27, 442.5, 135, 0));
		new Princess(new Location(Bukkit.getWorlds().get(0), 1544.5, 15, 441.5, 0, 0));
		new Princess(new Location(Bukkit.getWorlds().get(0), 1643.5, 9, 395.5, -45, 0));
		new Princess(new Location(Bukkit.getWorlds().get(0), 1692.5, 16, 464.5, 0, 0));
		new Princess(new Location(Bukkit.getWorlds().get(0), 1569.5, 9, 563.5, -135, 0));
		
		new Princess(MTP.getPrincessChurchSpawn());
		

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
		pm.registerEvents(new ListenerFade(), plugin);
		
		
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
		
		
		/** Remove princesses **/
		
		for(Princess p : secondary) {
			p.getArmorStand().remove();
		}
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


	/** Secondary princesses **/
	
	public static List<Princess> getSecondaryPrincesses() {
		return secondary;
	}


	/** Needed points to win **/
	
	public static int neededPointsToWin() {
		return 1;
	}


	/** Princess church spawn **/
	
	public static Location getPrincessChurchSpawn() {
		return new Location(Bukkit.getWorlds().get(0), 1590.5, 10, 329.5, 0, 0);
	}
	
	
	/** Church spawn **/
	
	public static Location getChurchSpawn() {
		return new Location(Bukkit.getWorlds().get(0), 1591.5, 9, 346.5, -180, 0);
	}
}