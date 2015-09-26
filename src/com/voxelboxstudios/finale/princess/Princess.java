package com.voxelboxstudios.finale.princess;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.voxelboxstudios.finale.MTP;
import com.voxelboxstudios.finale.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Princess {

    /** Texts **/

    private List<String> texts;

    
    /** Armor stand **/
    
    private ArmorStand as;
    
    
    /** Constructor **/

    public Princess(Location loc) {
        /** Texts **/

        texts = new ArrayList<String>();

        texts.add("Ich bin beeindruckt. Aber das geht besser!");
        texts.add("Nicht schlecht, für den Anfang.");
        texts.add("Ihr seid wahrlich würdig, mein Gatte zu werden!");
        texts.add("Ob mein Vater das toleriert?");
        texts.add("Ein spannendes Spiel. Möge der Beste mein Gatte werden!");
        
        
        /** Armor stand **/
		
		as = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwner("michirar");
		skull.setItemMeta(meta);
		
		as.setHelmet(skull);
		
		as.setChestplate(Util.getColoredLeatherItem(Material.LEATHER_CHESTPLATE, "Brustplatte", Color.fromBGR(242, 149, 233)));
		as.setLeggings(Util.getColoredLeatherItem(Material.LEATHER_LEGGINGS, "Hoste", Color.fromBGR(242, 149, 233)));
		as.setBoots(Util.getColoredLeatherItem(Material.LEATHER_BOOTS, "Stiefel", Color.fromBGR(242, 149, 233)));
	
		as.setArms(true);
		
		as.setCustomName("§dPrinzessin");
		as.setCustomNameVisible(true);
		
		
		/** Add to secondary **/
		
		MTP.getSecondaryPrincesses().add(this);
    }
    
    
    /** Get armor stand **/
    
    public ArmorStand getArmorStand() {
    	return as;
    }


    /** Speak **/

    public void speak() {
        /** Texts **/

        List<String> newtexts = new ArrayList<String>(texts);


        /** Shuffle **/

        Collections.shuffle(newtexts);


        /** Message **/

        String message = newtexts.get(0);


        /** Broadcast **/

        speak(message);
    }

    public void speak(String s) {
        Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "Prinzessin " + ChatColor.YELLOW + "» " + ChatColor.GRAY + "§o" + s);
    }
}
