package fr.midahe.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.midahe.sqlConnection.SqlMethods;

public class CommandAddMoney implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String aliase, String[] args) {
		
		if(sender instanceof Player && sender.hasPermission("command.addmoney")) {
			sender.sendMessage("§4[Erreur]: §cVous n'avez pas la permission");
			return true;
		}
		
		if(args.length != 2) {
			sender.sendMessage("§cUsage: /" + aliase + " <player> <montant>");
			return true;
		}
		
		try {
			int amount = Integer.valueOf(args[1]);
			Player target = Bukkit.getPlayer(args[0]);
			
			if(amount < 0) {
				sender.sendMessage("§4[Erreur]: §c" + amount + " doit être au dessus de 0");
				return true;
			}
			
			if(target != null) {
				new SqlMethods().addMoney(target, amount);
				sender.sendMessage("§eVous venez de donner §c" + amount + " §eà §b" + target.getName()
						+ '\n' + "    §6Il a maintenant §a" + new SqlMethods().getBalance(target) + " §6coins");
			}else {
				sender.sendMessage("§4[Erreur]: §cle joueur " + args[0] + " n'est pas connecté");
			}
		}catch(NumberFormatException e) {
			sender.sendMessage("§4[Erreur]: §c" + args[1] + " n'est pas un nombre valide");
		}
		
		return true;
	}
}
