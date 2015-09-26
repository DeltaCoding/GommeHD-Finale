package com.voxelboxstudios.finale.minigame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;
import com.voxelboxstudios.finale.MTP;

public class MinigamePest extends Minigame {

	/** Armor **/
	
	private ItemStack deadlord;
	private ItemStack helpers;
	
	private ItemStack chest;
	private ItemStack leggins;
	private ItemStack boots;
	
	/** Playerlist **/
	
	private List<Player> playerlist;
	
	/** Deadlist **/
	
	private List<Player> deadlist;
	
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
		
		playerlist = new ArrayList<>(attenders);
		Collections.shuffle(playerlist);
		dead = playerlist.get(0);
		Bukkit.broadcastMessage(MTP.PREFIX + dead.getName() + " ist mit der Pest infiziert!");
		
		this.deadlord = new ItemStack(Material.SKULL_ITEM, 0, (byte) 1);
		this.helpers = new ItemStack(Material.SKULL_ITEM, 0, (byte) 0);
		
		ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE, 0);
		LeatherArmorMeta meta2 = (LeatherArmorMeta) chest.getItemMeta();
		meta2.setColor(Color.BLACK);
		chest.setItemMeta(meta2);
		this.chest = chest;
		
		ItemStack leggins = new ItemStack(Material.LEATHER_LEGGINGS, 0);
		LeatherArmorMeta meta3 = (LeatherArmorMeta) leggins.getItemMeta();
		meta3.setColor(Color.BLACK);
		leggins.setItemMeta(meta3);
		this.leggins = leggins;
		
		ItemStack feet = new ItemStack(Material.LEATHER_BOOTS, 0);
		LeatherArmorMeta meta4 = (LeatherArmorMeta) feet.getItemMeta();
		meta4.setColor(Color.BLACK);
		feet.setItemMeta(meta4);
		this.boots = feet;
		
		dead.getInventory().setHelmet(this.deadlord);
		dead.getInventory().setChestplate(this.chest);
		dead.getInventory().setLeggings(this.leggins);
		dead.getInventory().setBoots(this.boots);
		
	}
	
	/** Dead on click **/
	
	@EventHandler
    public void onClick(EntityDamageByEntityEvent e) {
		
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			
			if(e.getDamager().equals(dead) || deadlist.contains(e.getDamager())) {
				
				if(deadlist.contains(e.getEntity()) || e.getEntity().equals(dead)) {
					e.setCancelled(true);
				} else {
					
					e.setDamage(0D);
					Player p = (Player) e.getEntity();
					
					deadlist.add(p);
					p.sendMessage(MTP.PREFIX + "Du bist nun auch infiziert!");
					
					p.getInventory().setHelmet(this.helpers);
					p.getInventory().setChestplate(this.chest);
					p.getInventory().setLeggings(this.leggins);
					p.getInventory().setBoots(this.boots);
					
					if(deadlist.size() == playerlist.size()-1) {
						for(Player pl : deadlist) {
							playerlist.remove(pl);
						}
					}
					
					win(playerlist.get(0));
					
				}
				
			}
					
		}
				
	}
	
	/** Check win **/
    
    private void win(Player last) {
    	/** Check player **/
    	
    	Player p = last;
    	
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
}
