package fr.midahe.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.midahe.sqlConnection.SqlMethods;

public class CommandRemoveMoney implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String aliase, String[] args) {
		
		if(sender instanceof Player && sender.hasPermission("command.removemoney")) {
			sender.sendMessage("§4[Erreur]: §cVous n'avez pas la permission");
			return true;
		}

		if (args.length != 2) {
			sender.sendMessage("§cUsage:   /" + aliase + "   <player>   <montant>");
			return true;
		}

		try {
			int amount = Integer.valueOf(args[1]);
			Player target = Bukkit.getPlayer(args[0]);

			if (amount < 0) {
				sender.sendMessage("§4[Erreur]:   §c" + amount + "   doit   être   au   dessus   de   0");
				return true;
			}

			if (target != null) {
				if (new SqlMethods().removeMoney(target, amount)) {
					sender.sendMessage("§eVous   venez   de   retirer   §c" + amount + "   §eà   §b" + target.getName()
							+ '\n' + "       §6Il   lui   reste   maintenant   §a" + new SqlMethods().getBalance(target)
							+ "   §6coins");
				} else {
					sender.sendMessage("§4[Erreur]:   §cle   joueur   §b" + target.getName()
							+ "   §cn'a   pas   assez   d'argent   :   " + '\n'
							+ "       §6Il   ne   lui   reste   que   §a" + new SqlMethods().getBalance(target)
							+ "   §6coins");
				}
			} else {
				sender.sendMessage("§4[Erreur]:   §cle   joueur   " + args[0] + "   n'est   pas   connecté");
			}
		} catch (NumberFormatException e) {
			sender.sendMessage("§4[Erreur]:   §c" + args[1] + "   n'est   pas   un   nombre   valide");
		}

		return true;
	}
}
