package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import instance.Time;

public class CommandTime implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String aliase, String[] args) {
		
		sender.sendMessage("§9Temps depuis le début de la partie: §6" + Time.getMinutes() + ":" + Time.getSeconds());
		return true;
	}
}
