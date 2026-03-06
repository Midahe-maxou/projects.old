package fr.midahe.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMlg implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String aliase, String[] args) {
		
		if(args.length == 0) {
			if(sender instanceof Player) {
				
				if(!sender.hasPermission("command.mlg.self")) {
					sender.sendMessage("§4[Erreur]: §cVous n'avez pas la permission");
					return true;
				}
				
				Player player = (Player)sender;
				Location loc = player.getLocation();
				player.teleport(new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY()+100, loc.getZ()));
				player.sendMessage("§bPrêt pour un §5MLG §b?");
			}else {
				sender.sendMessage("§4[Erreur]: §cVous devez etre un joueur pour faire cela");
			}
		}else if(args.length == 1) {
			
			if(!sender.hasPermission("command.mlg.other")) {
				sender.sendMessage("§4[Erreur]: §cVous n'avez pas la permission");
				return true;
			}
			
			if(!sender.hasPermission("op")) {
				sender.sendMessage("§4[Erreur]: §cVous n'avez pas la permission de faire cela");
				return true;
			}
			
			Player target = Bukkit.getPlayer(args[0]);
			if(target != null) {
				
				Location loc = target.getLocation();
				target.teleport(new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY()+100, loc.getZ()));
				target.sendMessage("§bReady to §5MLG §b?");
				
				sender.sendMessage("§f" + target.getName() + " §bva essayer un §5MLG");
			}
		}
		return false;
	}
}
