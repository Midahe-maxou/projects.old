package fr.midahe.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.midahe.inventory.MainShop;

public class CommandShop implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String aliase, String[] args) {
		
		if(sender instanceof Player) {
		
		Player player = (Player)sender;
		new MainShop(player);
		
		}else {
		sender.sendMessage("§4[Erreur]: Vous devez etre un joueur pour executer cette commande");
		}
		return true;
	}
}