package com.voxelboxstudios.finale.minigame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.voxelboxstudios.finale.MTP;
import com.voxelboxstudios.finale.util.Util;

public class MinigameTargetShooting extends Minigame {

	/** Chickens **/
	
	private List<Entity> chickens = new ArrayList<Entity>();
	
	
	/** Teleported **/
	
	private boolean teleported;
	
	
	/** Points **/
	
	private Map<String, Integer> points = new HashMap<String, Integer>();


	/** Bukkit task **/
	
	private BukkitTask runnable;
	
	
	/** Get name **/
	
	@Override
	public String getName() { 
		return "Zielschießen";
	}


	/** Get description **/

	@Override
	public String getDescription() {
		return "Treffe möglichst viele Tontauben, um der Prinzessin deine Zielsicherheit zu beweisen!";
	}

	
	/** Start **/
	
	@Override
	public void startGame(List<Player> attenders) {
		/** Teleported **/
		
		teleported = false;
		
		
		/** Chickens **/
		
		chickens.clear();
		
		
		/** Points **/
		
		points.clear();
		
		
		/** Possible locations **/
		
		List<Location> locations = new ArrayList<Location>();
		
		locations.add(new Location(Bukkit.getWorlds().get(0), 1638.5, 13, 406.5));
		locations.add(new Location(Bukkit.getWorlds().get(0), 1642.5, 13, 398.5));
		locations.add(new Location(Bukkit.getWorlds().get(0), 1636.5, 14, 406.5));
		locations.add(new Location(Bukkit.getWorlds().get(0), 1640.5, 12, 414.5));
		locations.add(new Location(Bukkit.getWorlds().get(0), 1649.5, 12, 416.5));
		locations.add(new Location(Bukkit.getWorlds().get(0), 1655.5, 13, 411.5, 90, 0));
		locations.add(new Location(Bukkit.getWorlds().get(0), 1660.5, 13, 421.5, 90, 0));
		locations.add(new Location(Bukkit.getWorlds().get(0), 1648.5, 13, 423.5));
		locations.add(new Location(Bukkit.getWorlds().get(0), 1641.5, 13, 422.5));
		locations.add(new Location(Bukkit.getWorlds().get(0), 1644.5, 13, 411.5));
		
		
		/** Shuffle **/
		
		Collections.shuffle(locations);
		
		
		/** Loop **/
		
		int index = 0;
		
		for(Player p : attenders) {
			/** Teleport **/
			
			p.teleport(locations.get(index));
			
			
			/** Points **/
			
			points.put(p.getName(), 0);
			
			
			/** Add item **/
			
			ItemStack bow = Util.getShortItem(Material.BOW, "§6Bogen");
			
			bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
			
			p.getInventory().addItem(bow);
			p.getInventory().setItem(35, Util.getShortItem(Material.ARROW, "§6Pfeil"));
			
			
			/** Index **/
			
			index++;
		}
		
		
		/** Teleported **/
		
		teleported = true;
		
		
		/** Timer **/
    	
    	new BukkitRunnable() {
    		int count = 4;
    		
    		public void run() {
    			for(Player p : Bukkit.getOnlinePlayers()) {
    				if(count != 1) {
    					p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);
    				} else {
    					p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 3);
    				}
    			}
    			
    			if(count == 1) {
    				/** Spawn **/
    				
    				runnable = new BukkitRunnable() {
    					public void run() {
    						/** Get random location **/
    						
    						Location loc = getRandomLocation();
    						
    						
    						/** Spawn chicken **/
    						
    						chickens.add(loc.getWorld().spawnEntity(loc, EntityType.CHICKEN));
    						
    						
    						/** Remove chickens **/
    						
    						for(Entity e : new ArrayList<Entity>(chickens)) {
    							if(e.getLocation().clone().subtract(0, 1, 0).getBlock().getType() != Material.AIR) {
    								e.remove();
    								chickens.remove(e);
    							}
    						}
    					}
    				}.runTaskTimer(MTP.getPlugin(), 5L, 5L);
    				
    				cancel();
    			}
    			
    			count--;
    		}
    	}.runTaskTimer(MTP.getPlugin(), 20L, 20L);
	}
	
	
	/** Get random location **/
	
	public Location getRandomLocation() {
		Random r = new Random();
		
		return new Location(Bukkit.getWorlds().get(0), 1647 + r.nextInt(40) - 20, 32 + r.nextInt(8), 411 + r.nextInt(40) - 20);
	}
	
	
	/** Damage **/
	
	@EventHandler
	public void onDamageByEntity(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Chicken) {
			if(e.getDamager() instanceof Arrow) {
				/** Arrow **/
				
				Arrow a = (Arrow) e.getDamager();
				
				if(a.getShooter() instanceof Player) {
					/** Player **/
					
					Player p = (Player) a.getShooter();
					
					
					/** Remove from list **/
					
					if(chickens.contains(e.getEntity())) chickens.remove(e.getEntity());
					
					
					/** Remove **/
					
					e.getEntity().remove();
					
					
					/** Give player point **/
					
					points.put(p.getName(), points.get(p.getName()) + 1);
					
					
					/** Play sound **/
					
					p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 3);
					
					
					/** Title **/
					
					Util.sendTitle(p, 5, 10, 5, "", "§eGetroffen!");
					
					
					/** Level and exp **/
					
					p.setLevel(points.get(p.getName()));
					p.setExp((1f / 15) * points.get(p.getName()));
					
					
					/** Check win **/
					
					checkWin();
				}
			}
		}
	}
	
	
	/** Check win **/
	
	private void checkWin() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(points.containsKey(p.getName())) {
				if(points.get(p.getName()) == 15) {
					/** Clear inventories **/
					
					for(Player t : Bukkit.getOnlinePlayers()) {
						t.getInventory().clear();
						
						t.setLevel(0);
						p.setExp(0f);
					}
					
					
					/** Bukkit task **/
					
					runnable.cancel();
					
					
					/** Remove chickens **/
					
					for(Entity e : chickens) {
						if(!e.isDead()) e.remove();
					}
					
					
					/** Broadcast **/
		        	
		        	Bukkit.broadcastMessage(MTP.PREFIX + "§e" + p.getName() + " §7hat gewonnen!");
					
		        	
		        	/** Sounds **/
		        	
		        	for(Player tp : Bukkit.getOnlinePlayers()) {
		        		if(tp == p) {
		        			/** Points **/
	    		        	
	    		        	MTP.points.put(p.getName(), MTP.points.get(p.getName()) + 1);
	    		        	
		        			tp.playSound(tp.getLocation(), "win", 1, 1);
		        		} else
		        			tp.playSound(tp.getLocation(), "lose", 1, 1);
		        	}
		        	
		        	
		        	/** Runnable **/
		        	
		        	new BukkitRunnable() {
		        		public void run() {
		        			end();
		        		}
		        	}.runTaskLater(MTP.getPlugin(), 10 * 20L);
		        	
					
					/** Break **/
					
					break;
				}
			}
		}
	}


	/** Damage **/
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) e.setCancelled(true);
		
		e.setDamage(0D);
	}


	/** Move **/
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if(!teleported) return;
		if(MTP.getSpectators().contains(e.getPlayer().getName())) return;
		
		if(e.getTo().getX() != e.getFrom().getX() || e.getTo().getY() != e.getFrom().getY() || e.getTo().getZ() != e.getFrom().getZ()) {
			e.setTo(e.getFrom());
		}
	}
	
	
	/** Get location **/
	
	@Override
	public Location getLocation() {
		return null;
	}


	/** Get spectator location **/
	
	@Override
	public Location getSpectatorLocation() {
		return new Location(Bukkit.getWorlds().get(0), 1646.5, 9, 412.5);
	}
}
