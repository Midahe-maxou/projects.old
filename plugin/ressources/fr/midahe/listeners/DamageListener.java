package fr.midahe.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {
	
	@EventHandler
	public void onTookDamage(EntityDamageEvent e) {
		
		if(e.getEntity() instanceof Player) {
			
			Player player = (Player)e.getEntity();
			
			player.sendMessage("§4[Damage]: §cYou took §6" + e.getFinalDamage()/2 + " §chearts damage by §e" + e.getCause().toString().toLowerCase());
		}
	}
}
