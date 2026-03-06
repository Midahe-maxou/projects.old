package fr.midahe.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandFeed implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String aliase, String[] args) {
		
		if(args.length == 0) {
			if(sender instanceof Player) {
				if(!sender.hasPermission("command.feed.self")) {
					sender.sendMessage("§4[Erreur] §cVous n'avez pas la permission");
					return true;
				}
				
				Player player = (Player)sender;
				
				player.setFoodLevel(20);
				player.setSaturation(20.0f);
				player.sendMessage("§aVous venez de vous feed");
				
			}
		}else if(args.length == 1) {
			if(!sender.hasPermission("command.feed.other")) {
				sender.sendMessage("§4[Erreur] §cVous n'avez pas la permission");
				return true;
			}
			
			Player target = Bukkit.getPlayer(args[0]);
			
			if(target != null) {
				
				target.setFoodLevel(20);
				target.setSaturation(20.0f);
				target.sendMessage("§eOn vient de vous §afeed");
				
				sender.sendMessage("§aVous venez de §afeed §b" + target.getName());
				
			}else {
				sender.sendMessage("§4[Erreur]: §cLe joueur " + args[0] + " n'est pas connecté");
			}
			
		}
		
		
		return true;
	}
}
