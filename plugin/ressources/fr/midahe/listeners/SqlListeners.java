package fr.midahe.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.midahe.sqlConnection.SqlMethods;

public class SqlListeners implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		SqlMethods sql = new SqlMethods();
		Player player = e.getPlayer();

		if (!sql.hasAccount(player)) {
			
			sql.createAccount(player);
			Bukkit.broadcastMessage("§bBienvenue à §e" + player.getName() + " §bsur le serveur");
		}
	}
}
