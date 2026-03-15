package listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import main.Variables;

public class BlockListener implements Listener {
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(!Variables.gameStarted) e.setCancelled(true);
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(!Variables.gameStarted) e.setCancelled(true);
	}
}
