package fr.midahe.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandDay implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String aliase, String[] args) {
		
		
		if(args.length == 0) {
			Bukkit.getWorld("world").setTime(2000);
			Bukkit.getWorld("world").setStorm(false);
			Bukkit.getWorld("world").setThundering(false);
			
			Bukkit.broadcastMessage("§eL'heure à été changé par §6" + sender.getName() + " §eet il est donc maintenant §610:00");
			
		}else if(args.length == 1 && args[0].equalsIgnoreCase("help")){
			sender.sendMessage(
					"§a[Help]: §3/day" + '\n'
					+ "  §6Usage: §r/day");
			return true;
		}else {
			sender.sendMessage("§cUsage: /day");
			return true;
		}
		return true;
	}

}
