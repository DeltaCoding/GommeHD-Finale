package com.voxelboxstudios.finale.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class ListenerWeather implements Listener {

    /** Weather **/

    @EventHandler
    public void onWeather(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

}
