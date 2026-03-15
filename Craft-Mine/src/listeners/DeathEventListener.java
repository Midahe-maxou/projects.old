package listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import main.Config;
import main.Main;

public class DeathEventListener implements Listener {
	
	Main main;
	
	public DeathEventListener(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player player = e.getEntity();
		
		e.setDeathMessage("§b" + player.getName() + " §5est mort");
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player player = e.getPlayer();
		
		e.setRespawnLocation(Config.startArea);
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 2));
			}
		}.runTaskLater(main, 1);
	}
}
