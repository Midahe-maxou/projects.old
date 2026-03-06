package fr.midahe.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class PlayerTakeDamagelistener implements Listener {
	
	@EventHandler
	public void onTakeDamage(EntityDamageByEntityEvent e) {
	
		if(e.getEntity() instanceof Player) {
			
			if(e.getDamager() instanceof Player) {
				Player player = (Player) e.getEntity();
				
				if(e.getCause() != DamageCause.ENTITY_ATTACK) {
					player.setNoDamageTicks(20);
					player.setMaximumNoDamageTicks(20);
					return;
				}
			
			player.setNoDamageTicks(0);
			player.setMaximumNoDamageTicks(0);
			}
		}
	}
}
