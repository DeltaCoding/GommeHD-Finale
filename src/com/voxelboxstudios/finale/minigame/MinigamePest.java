package com.voxelboxstudios.finale.minigame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.voxelboxstudios.finale.MTP;
import com.voxelboxstudios.finale.util.Util;

public class MinigamePest extends Minigame {

	/** Task **/
	
	private List<BukkitTask> ID;
	
	/** Armor **/
	
	private ItemStack deadlord;
	private ItemStack helpers;
	
	private ItemStack chest;
	private ItemStack leggins;
	private ItemStack boots;
	
	/** Playerlists **/
	
	private List<Player> playerlist;
	private List<Player> team_dead;
	private List<Player> team_alive;
		
	/** Dead **/
	
	private Player dead;
	
	/** Get name **/
	
	@Override
	public String getName() { 
		return "Pest";
	}


	/** Get description **/

	@Override
	public String getDescription() {
		return "Versuche der Pest aus dem Weg zu gehen indem du vor Infizierten wegrennst!";
	}

	
	/** Start **/
	
	@Override
	public void startGame(List<Player> attenders) {
		
		ID = new ArrayList<>();
		playerlist = new ArrayList<>(attenders);
		team_alive = new ArrayList<>(attenders);
		team_dead = new ArrayList<>();
		Collections.shuffle(playerlist);
		dead = playerlist.get(0);
		team_alive.remove(dead);
		team_dead.add(dead);
		
		Util.sendTitle(dead, 10, 20, 10, "§6Du bist der Tod!", "");
		Bukkit.broadcastMessage(MTP.PREFIX + dead.getName() + " ist mit der Pest infiziert!");
		
		this.deadlord = new ItemStack(Material.SKULL_ITEM, 1, (byte) 1);
		this.helpers = new ItemStack(Material.SKULL_ITEM, 1, (byte) 0);
		
		ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		LeatherArmorMeta meta2 = (LeatherArmorMeta) chest.getItemMeta();
		meta2.setColor(Color.BLACK);
		chest.setItemMeta(meta2);
		this.chest = chest;
		
		ItemStack leggins = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		LeatherArmorMeta meta3 = (LeatherArmorMeta) leggins.getItemMeta();
		meta3.setColor(Color.BLACK);
		leggins.setItemMeta(meta3);
		this.leggins = leggins;
		
		ItemStack feet = new ItemStack(Material.LEATHER_BOOTS, 1);
		LeatherArmorMeta meta4 = (LeatherArmorMeta) feet.getItemMeta();
		meta4.setColor(Color.BLACK);
		feet.setItemMeta(meta4);
		this.boots = feet;
		
		dead.getInventory().setHelmet(this.deadlord);
		dead.getInventory().setChestplate(this.chest);
		dead.getInventory().setLeggings(this.leggins);
		dead.getInventory().setBoots(this.boots);
		
		ID.add(new BukkitRunnable() {
			
			int timer = 20;
			
			public void run() {
				
				for(Player all : Bukkit.getOnlinePlayers()) {
					
					all.setLevel(timer);
					all.setExp(0);
					all.setExp((1f/20) * timer);
					
				}
				
				timer--;
				
			}
			
		}.runTaskTimer(MTP.getPlugin(), 0L, 20L));
		
		ID.add(new BukkitRunnable() {
			
			public void run() {
				
				Bukkit.broadcastMessage(MTP.PREFIX + "§e" + "Die Spieler" + " §7haben gewonnen!");
				
				for(Player tp : Bukkit.getOnlinePlayers()) {
	        		if(tp == dead) {
	        			/** Points **/
    		        	
    		        	MTP.points.put(tp.getName(), MTP.points.get(tp.getName()) + 1);
	        			tp.playSound(tp.getLocation(), "win", 1, 1);
	        		} else
	        			tp.playSound(tp.getLocation(), "lose", 1, 1);
	        	}
				
				new BukkitRunnable() {
	        		public void run() {
	        			end();
	        		}
	        	}.runTaskLater(MTP.getPlugin(), 10 * 20L);
				
			}
			
		}.runTaskLater(MTP.getPlugin(), 20L*45));
		
	}
	
	/** Dead on click **/
	
	@EventHandler
    public void onClick(EntityDamageByEntityEvent e) {
		
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			
			if(team_dead.contains(e.getEntity()) && team_dead.contains(e.getDamager()) || team_alive.contains(e.getEntity()) && team_alive.contains(e.getDamager())) {
				e.setDamage(0D);
				e.setCancelled(true);
			} else {
					
				if(team_dead.contains(e.getDamager())) {
						
					e.setDamage(0D);
					Player p = (Player) e.getEntity();
						
					team_alive.remove(p);
					team_dead.add(p);

					Util.sendTitle(p, 10, 20, 10, "§cDu wurdest Infiziert!", "");
					Bukkit.broadcastMessage(MTP.PREFIX + "§e" + p.getName() + " §7wurde infiziert!");
					p.playSound(p.getLocation(), Sound.ENDERMAN_DEATH, 1, 1);
					
					p.getInventory().setHelmet(this.helpers);
					p.getInventory().setChestplate(this.chest);
					p.getInventory().setLeggings(this.leggins);
					p.getInventory().setBoots(this.boots);
					
					if(team_alive.size() == 0) {
						
						Bukkit.broadcastMessage(MTP.PREFIX + "§e" + "Der Tod" + " §7hat gewonnen!");
						
						for(Player tp : Bukkit.getOnlinePlayers()) {
			        		if(tp == dead) {
			        			tp.playSound(tp.getLocation(), "win", 1, 1);
			        			
			        			/** Points **/
		    		        	
		    		        	MTP.points.put(tp.getName(), MTP.points.get(tp.getName()) + 1);
			        		} else
			        			tp.playSound(tp.getLocation(), "lose", 1, 1);
			        	}
						
						for(BukkitTask ids : ID) {
							ids.cancel();
						}
						
						new BukkitRunnable() {
			        		public void run() {
			        			end();
			        		}
			        	}.runTaskLater(MTP.getPlugin(), 10 * 20L);
			        	
					}
						
				}
					
			}
									
		}
				
	}
	
	/** Get location **/
	
	@Override
	public Location getLocation() {
		Random r = new Random();
    	
        return new Location(Bukkit.getWorlds().get(0), 1543.5 + r.nextInt(32) - 16, 9, 472.5 + r.nextInt(60) - 30);
	}


	@Override
	public Location getSpectatorLocation() {
		return new Location(Bukkit.getWorlds().get(0), 1543.5, 9, 472.5);
	}
}
