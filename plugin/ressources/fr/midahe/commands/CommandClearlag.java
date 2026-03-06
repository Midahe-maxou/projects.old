package fr.midahe.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;

public class CommandClearlag implements CommandExecutor {

	ArrayList<Entity> arr = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String aliase, String[] args) {

		if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
			sender.sendMessage("§a[Help]: §3/clearlag" + '\n' + "  §6Usage: §r/clearlag");
			return true;
		}

		if (args.length > 0) {
			sender.sendMessage("§cUsage: /clearlag");
			return true;
		}

		arr = (ArrayList<Entity>) Bukkit.getWorld("world").getEntities();
		int number = 0;

		for (Entity e : arr) {
			if (e instanceof Item) {
				e.remove();
				number++;
			}
		}

		if (number == 0) {
			Bukkit.broadcastMessage("§4[ClearLag]: §6Aucun item n'a été enlevé");
		} else if (number == 1) {
			Bukkit.broadcastMessage("§4[ClearLag]: §c1 §6item a été enlevé");
		} else {
			Bukkit.broadcastMessage("§4[ClearLag]: §c" + number + " §6items ont été enlevé");
		}

		return true;
	}
}
