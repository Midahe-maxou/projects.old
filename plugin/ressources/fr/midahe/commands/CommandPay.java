package fr.midahe.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.midahe.sqlConnection.SqlMethods;

public class CommandPay implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String aliase, String[] args) {

		if (sender instanceof Player) {
			Player player = (Player) sender;

			if (args.length != 2) {
				player.sendMessage("§cUsage: /" + aliase + " <player> <montant>");
				return true;
			}

			try {
				int amount = Integer.valueOf(args[1]);
				Player target = Bukkit.getPlayer(args[0]);
				if (target != null) {

					if (amount < 0) {
						sender.sendMessage("§4[Erreur]: §c" + amount + " doit être au dessus de 0");
						return true;
					}

					if (new SqlMethods().removeMoney(player, amount)) {
						new SqlMethods().addMoney(target, amount);

						player.sendMessage("§eVous venez de donner §c" + amount + " §ecoins à §b" + target.getName());
						target.sendMessage("§b" + player.getName() + " §eVient de vous donner §c" + amount + " §ecoins");

					} else {
						player.sendMessage("§cVous n'avez pas assez d'argent");
					}
				} else {
					player.sendMessage("§4[Erreur]: le joueur " + args[0] + " n'est pas connecté");
				}
			} catch (NumberFormatException e) {
				player.sendMessage("§4[Erreur]: §c" + args[1] + " n'est pas un nombre");
			}
		} else {
			sender.sendMessage("§4[Erreur]: §cVous devez etre un joueur pour faire cela");
		}
		return true;
	}
}