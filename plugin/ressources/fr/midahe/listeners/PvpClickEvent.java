package fr.midahe.listeners;

import org.bukkit.event.Listener;

public class PvpClickEvent implements Listener {
	
/*	@EventHandler
	public void onClickEvent(EntityDamageByEntityEvent e) {
		
		if(e.getDamager() instanceof Player) {
			Player player = (Player)e.getDamager();
			Vector direction = player.getLocation().getDirection();
			if(e.getEntity().getLocation().getDirection().isInSphere(direction, 3)) {
			}
		}
	}
	
	@EventHandler
	public void onClickEvent(PlayerInteractEvent e) {
		
		Player player =  e.getPlayer();
		Location location = player.getLocation();
		
		if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			for(Entity target : Bukkit.getWorld("world").getEntities()) {
				if(target != null && target == player) {
					return;
				}
			}
		}
	}*/
}