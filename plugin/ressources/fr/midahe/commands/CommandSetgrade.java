package fr.midahe.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.midahe.sqlConnection.SqlMethods;

public class CommandSetgrade implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String aliase, String[] args) {

// apprenti | sorcier | mage | devin | necromancien

		if (sender instanceof Player && !sender.hasPermission("command.setgrade")) {
			sender.sendMessage("§4[Erreur]: §cVous n'avez pas la permission");
			return true;
		}

		if (args.length == 2) {

			Player target = Bukkit.getPlayer(args[0]);

			if (target != null) {

				switch (args[1].toLowerCase()) {

				case "apprenti":
					new SqlMethods().setgrade(target, "apprenti");
					message(sender, target, args[1]);
					break;

				case "sorcier":
					new SqlMethods().setgrade(target, "sorcier");
					message(sender, target, args[1]);
					break;

				case "mage":
					new SqlMethods().setgrade(target, "mage");
					message(sender, target, args[1]);
					break;

				case "devin":
					new SqlMethods().setgrade(target, "devin");
					message(sender, target, args[1]);
					break;

				case "necromancien":
					new SqlMethods().setgrade(target, "necromancien");
					message(sender, target, args[1]);
					break;
					
				case "helpeur":
					new SqlMethods().setgrade(target, "helpeur");
					message(sender, target, args[1]);
					break;
					
				case "modo":
					new SqlMethods().setgrade(target, "modo");
					message(sender, target, args[1]);
					break;
					
				case "supermodo":
					new SqlMethods().setgrade(target, "supermodo");
					message(sender, target, args[1]);
					break;
					
				case "admin":
					new SqlMethods().setgrade(target, "admin");
					message(sender, target, args[1]);
					break;

				default:
					sender.sendMessage("§4[Erreur]: §cLe grade " + args[1] + " n'existe pas");
					break;
				}
			}

		} else {
			sender.sendMessage("§cUsage: /" + aliase + " <player> <grade>");
		}
		return true;
	}

	private void message(CommandSender sender, Player target, String args) {
		sender.sendMessage("§6Vous venez de mettre le grade §5" + args + " §6à §b" + target.getName());
		target.sendMessage("§6Vous venez d'avoir le grade §5" + args);
	}
}
