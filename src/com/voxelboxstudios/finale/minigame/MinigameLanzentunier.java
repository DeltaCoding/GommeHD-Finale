package com.voxelboxstudios.finale.minigame;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.voxelboxstudios.finale.MTP;
import com.voxelboxstudios.finale.util.ActionBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MinigameLanzentunier extends Minigame {

	/** On horses **/
	
	private boolean on;
	
	
    /** Horses **/

    private Map<String, Horse> horses = new HashMap<String, Horse>();
    
    
    /** Health **/
    
    private Map<String, Integer> health = new HashMap<String, Integer>();
    
    
    /** Count **/
    
    private int count;
    
    
    /** PVP **/
    
    private boolean pvp;


    /** Get name **/

    @Override
    public String getName() {
        return "Lanzentunier";
    }


    /** Get description **/

    @Override
    public String getDescription() {
        return "Reite auf einem Pferd und versuche die Mitstreiter mit deiner Lanze herunterzuwerfen. Zeige ihnen wo der Hammer hängt!";
    }


    /** Get location **/

    @Override
    public Location getLocation() {
    	Random r = new Random();
    	
        return new Location(Bukkit.getWorlds().get(0), 1543.5 + r.nextInt(32) - 16, 9, 472.5 + r.nextInt(60) - 30);
    }


    /** Start **/

    @Override
    public void startGame(List<Player> attenders) {
    	/** Count **/
    	
    	count = 4;
    	
    	
    	/** PVP **/
    	
    	pvp = false;
    	
    	
    	/** On **/
    	
    	on = false;
    	
    	
    	/** Timer **/
    	
    	new BukkitRunnable() {
    		public void run() {
    			for(Player p : Bukkit.getOnlinePlayers()) {
    				if(count != 1) {
    					p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);
    				} else {
    					p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 3);
    				}
    			}
    			
    			if(count == 1) {
    				pvp = true;
    				cancel();
    			}
    			
    			count--;
    		}
    	}.runTaskTimer(MTP.getPlugin(), 20L, 20L);
    	
    	
        /** Clear horses **/

        horses.clear();
        
        
        /** Clear players **/
        
        health.clear();
        
        
        /** Lanze **/
        
        ItemStack lanze = new ItemStack(Material.STICK, 1);
        ItemMeta lanzenmeta = lanze.getItemMeta();
        lanzenmeta.setDisplayName("§6Lanze");
        lanze.setItemMeta(lanzenmeta);


        /** Horse **/

        for(Player p : attenders) {
            /** Horse **/

            Horse h = (Horse) p.getWorld().spawnEntity(p.getLocation(), EntityType.HORSE);

            h.setAdult();

            h.setAgeLock(true);

            h.setTamed(true);

            h.setStyle(Style.NONE);
            
            h.setJumpStrength(0D);
            h.getInventory().setSaddle(new ItemStack(Material.SADDLE, 1));
            
            
            /** Add item **/
            
            p.getInventory().addItem(lanze);


            /** Set passenger **/

            h.setPassenger(p);


            /** Put into hashmap **/

            horses.put(p.getName(), h);
            
            
            /** Health **/
            
            health.put(p.getName(), 10);
        }
    
        
        /** On **/
        
        on = true;
    }

    
    /** Click **/
    
    @EventHandler
    public void onClick(InventoryClickEvent e) {
    	if(e.getWhoClicked().getGameMode() != GameMode.CREATIVE)
    		e.setCancelled(true);
    }
    

    /** Quit **/

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if(horses.containsKey(e.getPlayer().getName())) {
            horses.get(e.getPlayer().getName()).remove();
            horses.remove(e.getPlayer().getName());
        }
        
        if(health.containsKey(e.getPlayer().getName())) {
        	health.remove(e.getPlayer().getName());
        }
        
        checkWin(e.getPlayer());
    }


    /** Kick **/

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        if(horses.containsKey(e.getPlayer().getName())) {
            horses.get(e.getPlayer().getName()).remove();
            horses.remove(e.getPlayer().getName());
        }
        
        if(health.containsKey(e.getPlayer().getName())) {
        	health.remove(e.getPlayer().getName());
        }
        
        checkWin(e.getPlayer());
    }
    
    
    /** Check win **/
    
    private void checkWin(Player last) {
    	/** Check player **/
    	
    	Player p = check(last);
        
        if(p != null) {
        	/** Broadcast **/
        	
        	Bukkit.broadcastMessage(MTP.PREFIX + "§e" + p.getName() + " §7hat gewonnen!");
        	
        	
        	/** Sounds **/
        	
        	for(Player tp : Bukkit.getOnlinePlayers()) {
        		if(tp == p)
        			tp.playSound(p.getLocation(), "win", 1, 1);
        		else
        			tp.playSound(p.getLocation(), "lose", 1, 1);
        	}
        	
        	
        	/** Clear horses **/
        	
        	for(Horse h : horses.values())  {
        		for(int i = 0; i < 12; i++) h.getWorld().playEffect(h.getLocation(), Effect.ENDER_SIGNAL, i);
        		
        		h.remove();
        	}
        	
        	
        	/** Runnable **/
        	
        	new BukkitRunnable() {
        		public void run() {
        			end();
        		}
        	}.runTaskLater(MTP.getPlugin(), 10 * 20L);
        }
    }

    
    /** Damage **/

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
    	/** Cancel **/
    	
    	if(!pvp) {
    		e.setCancelled(true);
    		return;
    	}
    	
    	
    	/** Check if entity is a horse **/
    	
        if(e.getEntity() instanceof Horse) {
            /** If so, cancel event **/
        	
        	e.setCancelled(true);
            
        	
        	/** Check passenger **/
        	
            if(e.getEntity().getPassenger() != null) {
            	/** Cast to player **/
            	
            	Player p = ((Player) e.getEntity().getPassenger());
            	
            	
            	/** Damage **/
            	
            	damage(p);
            }
        }
        
        
        /** Check if entity is a player **/
        
        if(e.getEntity() instanceof Player) {
        	damage((Player) e.getEntity());
        }
        
        
        /** Set damage to 0 **/
        
        e.setDamage(0D);
    }
    
    
    /** Damage **/
    
    public void damage(Player p) {
    	/** Check if player has played the game **/
    	
    	if(health.containsKey(p.getName())) {
    		/** Subtract live **/
    		
    		health.put(p.getName(), health.get(p.getName()) - 1);
        	
    		
    		/** Play sound **/
    		
        	p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1, 3);
        	
        	
        	/** Action bar **/
        	
        	String ss = "§a";
        	
        	for(int i = 0; i < health.get(p.getName()); i++) {
        		ss = ss + "O";
        	}
        	
        	ActionBar.sendActionBar(p, ss);
        	
        	
        	/** Check if player has lost **/
        	
        	if(health.get(p.getName()) == 0) {
        		/** Set gamemode to spectator **/
        		
        		p.setGameMode(GameMode.SPECTATOR);
        		
        		
        		/** Set vertical velocity **/
        		
        		p.setVelocity(new Vector(0, 1, 0));
        		
        		
        		/** Play sound **/
        		
        		p.playSound(p.getLocation(), Sound.HORSE_ANGRY, 1, 3);
        		
        		
        		/** Eject **/
        		
        		horses.get(p.getName()).eject();
        		
        		p.eject();
        		
        		
        		/** Check if game has been won **/
        		
        		checkWin(p);
        	}

        	
        	/** Set damage to 0 **/
        	
        	p.damage(0D);
    	}
    }
    
    
    /** Check **/
    
    public Player check(Player last) {
    	/** Players alive **/
    	
    	int alive = 0;
    	
    	List<Player> players = new ArrayList<Player>();
    	
    	for(Player p : Bukkit.getOnlinePlayers()) {
    		if(!MTP.getSpectators().contains(p.getName())) {
    			if(health.containsKey(p.getName())) {
    				if(health.get(p.getName()) > 0) {
    					alive++;
    					players.add(p);
    				}
    			}
    		}
    	}
    	
    	
    	/** If only one player is alive, return him **/
    	
    	if(alive == 1) {
    		return players.get(0);
    	}
    	
    	
    	/** If no player is alive, return the last player **/
    	
    	if(alive == 0) {
    		return last;
    	}
    	
    	
    	/** Game hasn't been won yet **/
    	
    	return null;
    }


    /** Vehicle leave **/

    @EventHandler
    public void onLeave(VehicleExitEvent e) {
    	Player p = (Player) e.getExited();
    	
    	if(p.getGameMode() == GameMode.ADVENTURE) {
    		e.setCancelled(true);
    	}
    }
    
    
    /** Vehicle enter **/
    
    @EventHandler
    public void onEnter(VehicleEnterEvent e) {
    	if(on) e.setCancelled(true);
    }
}