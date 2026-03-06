package fr.midahe.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandNight implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String aliase, String[] args) {

		if (args.length == 0) {

			Bukkit.getWorld("world").setTime(14000);

			Bukkit.broadcastMessage(
					"§eL'heure à été changé par §6" + sender.getName() + " §eet il est donc maintenant §622:00");

		} else if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
			sender.sendMessage("§a[Help]: §3/night" + '\n' + "  §6Usage: §r/night");
			return true;
		} else {
			sender.sendMessage("§cUsage: /day");
			return true;
		}
		return true;
	}

}
