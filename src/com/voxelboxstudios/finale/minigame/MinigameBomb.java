package com.voxelboxstudios.finale.minigame;

import org.bukkit.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import com.voxelboxstudios.finale.MTP;
import com.voxelboxstudios.finale.util.Util;

public class MinigameBomb extends Minigame {	
	
	/** Bomb Player **/
	
	private Player bombed;
	
	
	/** Task **/
	
	private List<BukkitTask> ID;
	
	
	/** Playerlist **/
	
	private List<Player> playerlist;
	
	
	/** Get name **/
	
	@Override
	public String getName() { 
		return "Explosiv";
	}


	/** Get description **/

	@Override
	public String getDescription() {
		return "Gebe die Bombe bevor sie explodiert an einen anderen Spieler ab!";
	}

	
	/** Start **/
	
	@Override
	public void startGame(List<Player> attenders) {
		
		ID = new ArrayList<>();
		playerlist = new ArrayList<>(attenders);
		randomTimer();
		
	}
	
	/** Fire Particles **/
	
	public void startParticles() {
		
		ID.add(new BukkitRunnable() {
			
			public void run() {
								
				MTP.getGameSpawn().getWorld().playEffect(bombed.getLocation(), Effect.LAVA_POP, 70);
				
			}
			
		}.runTaskTimer(MTP.getPlugin(), 10L, 10L));
		
	}
	
	/** RandomPlayer **/
	
	public void randomPlayer() {
		Collections.shuffle(playerlist);
		bombed = playerlist.get(0);
		
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwner("MHF_TNT2");
		skull.setItemMeta(meta);
		
		bombed.getInventory().setHelmet(skull);
		bombed.getInventory().setItem(0, new ItemStack(Material.TNT));
		
		startParticles();
	}
	
	/** RandomTimer **/
	
	public void randomTimer() {
		int time = new Random().nextInt(20) + 10;
				
		randomPlayer();
		runt(time, 0);
	}
	
	/** Game Runnable **/
	
	public void runt(final int t, final int a) {
		ID.add(new BukkitRunnable() {
			int time = t;
			int timea = a;
			
			public void run() {
								
				time--;
				
				if(time < 3) time = 3;
				
				timea++;
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1, 3);
				}
				
				if(timea == 50) {
					
					/** Create Firework **/
					
					Firework fw = (Firework) bombed.getWorld().spawnEntity(bombed.getLocation(), EntityType.FIREWORK);
		            FireworkMeta fwm = fw.getFireworkMeta();
		            FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(Color.RED).withColor(Color.YELLOW).withColor(Color.BLACK).with(Type.BALL).trail(false).build();
		           
		            fwm.addEffect(effect);
		            fwm.setPower(0);
		            fw.setFireworkMeta(fwm);  
		          
		            MTP.getGameSpawn().getWorld().playSound(bombed.getLocation(), Sound.EXPLODE, 1, 1);
					bombed.setGameMode(GameMode.SPECTATOR);
					bombed.setVelocity(new Vector(0.00D, 0.5D, 0.00D));
					
					Util.sendTitle(bombed, 10, 20, 10, "§cDu bist explodiert!", "");
					Bukkit.broadcastMessage(MTP.PREFIX + "§e" + bombed.getName() + " §7ist explodiert!");
		            
		            playerlist.remove(bombed);
					
		            if(playerlist.size() == 1) {
						win(playerlist.get(0));
						return;
					}
		            
		            /** Cancel Tasks **/
					
					for(BukkitTask ids : ID) {
						ids.cancel();
					}
					
					/** Choose new Bomb Player **/
		            
					randomTimer();
					
				} else {
					runt(time, timea);
				}
			}
			
		}.runTaskLater(MTP.getPlugin(), t));
	}
	
	/** Give Bomb on click **/
	
	@EventHandler
    public void onClick(EntityDamageByEntityEvent e) {
		
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			
			if(e.getDamager().equals(bombed)) {
				
				((Player) e.getDamager()).getInventory().setArmorContents(null);
				((Player) e.getDamager()).getInventory().clear(0);
				bombed = (Player) e.getEntity();

				ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
				SkullMeta meta = (SkullMeta) skull.getItemMeta();
				meta.setOwner("MHF_TNT2");
				skull.setItemMeta(meta);
				
				bombed.getInventory().setHelmet(skull);
				bombed.getInventory().setItem(0, new ItemStack(Material.TNT));
				
			}
					
		}
				
	}
	
	/** DamageNull **/
	
	@EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
		
		e.setDamage(0D);
		
	}
	
	/** Check win **/
    
    private void win(Player last) {
    	/** Check player **/
    	
    	Player p = last;
    	
    	/** Cancel Tasks **/
    	
    	for(BukkitTask ids : ID) {
    		ids.cancel();
    	}
        
        if(p != null) {
        	/** Broadcast **/
        	
        	Bukkit.broadcastMessage(MTP.PREFIX + "§e" + p.getName() + " §7hat gewonnen!");
        	
        	
        	/** Points **/
        	
        	MTP.points.put(p.getName(), MTP.points.get(p.getName()) + 1);
        	
        	
        	/** Sounds **/
        	
        	for(Player tp : Bukkit.getOnlinePlayers()) {
        		if(tp == p)
        			tp.playSound(tp.getLocation(), "win", 1, 1);
        		else
        			tp.playSound(tp.getLocation(), "lose", 1, 1);
        	}
        	        	
        	/** Runnable **/
        	
        	new BukkitRunnable() {
        		public void run() {
        			end();
        		}
        	}.runTaskLater(MTP.getPlugin(), 10 * 20L);
        }
    }

    
	/** Get location **/
	
	@Override
	public Location getLocation() {
		Random r = new Random();
    	
        return new Location(Bukkit.getWorlds().get(0), 1543.5 + r.nextInt(32) - 16, 9, 472.5 + r.nextInt(60) - 30);
	}


	/** Spectator location **/
	
	@Override
	public Location getSpectatorLocation() {
		return new Location(Bukkit.getWorlds().get(0), 1543.5, 9, 472.5);
	}
}
