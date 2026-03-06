package fr.midahe.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.midahe.sqlConnection.SqlMethods;

public class CommandMoney implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String aliase, String[] args) {

		if (args.length == 0) {
			if (sender instanceof Player) {
				Player player = (Player) sender;

				int bal = new SqlMethods().getBalance(player);

				player.sendMessage("§6Vous avez §e" + bal + " §6coins");

			}
		} else if (args.length == 1) {
			
			Player target = Bukkit.getPlayer(args[0]);
			
			if(target != null) {
				int bal = new SqlMethods().getBalance(target);
				
				sender.sendMessage("§b" + target.getName() + " §6à §e" + bal + " §6coins");
				
			}else {
				sender.sendMessage("§4[Erreur]: §cLe joueur " + args[0] + " n'est pas connecté");
				return true;
			}
		}
		return true;
	}
}
