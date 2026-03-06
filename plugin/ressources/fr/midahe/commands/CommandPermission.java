package fr.midahe.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.midahe.instances.SetPermissions;
import fr.midahe.listeners.SetPermOnJoin;

public class CommandPermission implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String aliase, String[] args) {

		if (sender instanceof Player) {
			if (!sender.hasPermission("command.perm")) {
				sender.sendMessage("§4[Erreur]: §cVous n'avez pas la permission");
				return true;
			}
		}

		if (args.length == 0) {
			sender.sendMessage("§cUsage: /" + aliase + " <player> <permission> <true|false>");
		}

		if (args.length == 1 || args.length == 2) {

			Player target = Bukkit.getPlayer(args[0]);
			if (target != null) {
				sender.sendMessage("§cUsage: /" + aliase + " " + args[0] + " <permission> <true|false>");
			} else {
				sender.sendMessage("§4[Erreur]: le joueur " + args[0] + " n'est pas connecté");
			}
			return true;
		}
		if (args.length == 3) {
			Player target = Bukkit.getPlayer(args[0]);

			if (target != null) {

				for (String perm : SetPermissions.permissions) {
					if (perm != null && perm.equalsIgnoreCase(args[1])) {
						SetPermOnJoin.permi.get(target.getUniqueId()).setPermission(args[1], Boolean.valueOf(args[2]));
						if (Boolean.valueOf(args[2])) {
							target.sendMessage("§4[PERMISSION]: §aadd §5" + args[1]);
							sender.sendMessage("§4[PERMISSION]: §aadd §5" + args[1] + " §6to §b" + target.getName());
						} else {
							target.sendMessage("§4[PERMISSION]: §cremove §5" + args[1]);
							sender.sendMessage("§4[PERMISSION]: §cremove §5" + args[1] + " §6to §b" + target.getName());
						}
						return true;
					}
				}

				sender.sendMessage("§4[Erreur]: §cLa permission " + args[1] + " n'existe pas");

			} else {
				sender.sendMessage("§4[Erreur]: le joueur " + args[0] + " n'est pas connecté");
			}
		}
		return true;
	}
}
