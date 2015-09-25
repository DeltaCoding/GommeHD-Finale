package com.voxelboxstudios.finale.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class ListenerFood implements Listener {

	/** Food **/
	
	@EventHandler
	public void onFood(FoodLevelChangeEvent e) {
		e.setFoodLevel(20);
	}
	
}
