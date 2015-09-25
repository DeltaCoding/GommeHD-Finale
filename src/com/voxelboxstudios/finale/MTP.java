package com.voxelboxstudios.finale;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.voxelboxstudios.finale.listener.*;
import com.voxelboxstudios.finale.minigame.Minigame;
import com.voxelboxstudios.finale.minigame.MinigameManager;
import com.voxelboxstudios.finale.state.GameState;
import com.voxelboxstudios.finale.state.LobbyState;

public class MTP extends JavaPlugin {

	/** Prefix **/
	
	public static final String PREFIX = ChatColor.GOLD + "Merit the Princess " + ChatColor.YELLOW + "» " + ChatColor.GRAY;
	
	
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
	
	
	/** Enable **/
	
	public void onEnable() {
		/** Plugin **/
		
		plugin = this;
		
		
		/** State **/
		
		state = GameState.LOBBY;
		
		
		/** Minigame manager **/
		
		manager = new MinigameManager();
		
		
		/** Spectators **/
		
		spectators = new ArrayList<String>();
		
		
		/** Listeners **/
		
		PluginManager pm = Bukkit.getPluginManager();
		
		pm.registerEvents(new ListenerJoin(), plugin);
		pm.registerEvents(new ListenerLeave(), plugin);
		pm.registerEvents(new ListenerFood(), plugin);
		
		
		/** Lobby state **/
		
		new LobbyState(30);
	}

		
	/**Get spectators **/
	
	public static List<String> getSpectators() {
		return spectators;
	}
	
	
	/** Get manager **/
	
	public static MinigameManager getMinigameManager() {
		return manager;
	}
	
	
	/** Get minigame **/
	
	public static Minigame getMinigame() {
		return minigame;
	}
	
	
	/** Set minigame **/
	
	public static void setMinigame(Minigame newminigame) {		minigame = newminigame;
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
		return 4;
	}
}