package listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import main.Config;
import main.Variables;

public class ConnectionListener implements Listener {
	
	@EventHandler
	public void onConnect(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		
		if(!Variables.gameStarted) {
			player.teleport(Config.spawnArea);
			player.getInventory().clear();
		}

		e.setJoinMessage("§eLe joueur §b" + player.getName() + " §eViens de se §aconnecter");
		player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 2));
	}
	
	@EventHandler
	public void onDisconnect(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		
		e.setQuitMessage("§eLe joueur §b" + player.getName() + " §eViens de se §cdéconnecter");
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent e) {
		Player player = e.getPlayer();
		
		e.setLeaveMessage("§eLe joueur §b" + player.getName() + " §e vient d'être §ckick §epour la raison suivante: §7" + e.getReason());
	}
}
