package fr.midahe.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;


public class KillMobListener implements Listener {
	
	@EventHandler
	public void onKillMob(EntityDeathEvent e) {
		
		Entity entity = e.getEntity();
		Location loc = entity.getLocation();
		EntityType type = entity.getType();
		String name = entity.getName().toLowerCase();
		String[] named;
		String number = "";
		int i = 0;
		
		if(name.contains("§5")) {
			
			named = name.split("x");
			number = named[1];
			number = number.trim();
			
			try {
				i = Integer.valueOf(number);
			}catch(NumberFormatException exce) {
				exce.printStackTrace();
			}
			
			i = i - 1;
			if(i > 0) {
				Entity mob = Bukkit.getWorld("world").spawnEntity(loc, type);
				mob.setCustomName(named[0] + "x" + i);
			}
		}
	}
}
