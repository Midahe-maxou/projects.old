package fr.midahe.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandFly implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String aliase, String[] args) {

		if (args.length == 0) {
			if (sender instanceof Player) {
				if (!sender.hasPermission("command.fly.self")) {
					sender.sendMessage("§4[Erreur]: §cVous n'avez pas la permission");
					return true;

				}

				Player player = (Player) sender;
				if (!player.getAllowFlight()) {
					player.setAllowFlight(true);
					player.setFlying(true);
					player.sendMessage("§6Vous avez §aactivé §6le fly");
				} else {
					player.setAllowFlight(false);
					player.setFlying(false);
					player.sendMessage("§6Vous avez §cdésactivé §6le fly");
				}
			} else {
				sender.sendMessage("§4[Erreur]: §cVous devez etre un joueur pour faire cela");
			}

		} else if (args.length == 1) {

			if (!sender.hasPermission("command.fly.other")) {
				sender.sendMessage("§4[Erreur]: §cVous n'avez pas la permission");
				return true;
			}

			Player target = Bukkit.getPlayer(args[0]);
			if (target != null) {
				if (!target.getAllowFlight()) {
					target.setAllowFlight(true);
					target.setFlying(true);
					sender.sendMessage("§6Vous avez §aactivé §6le fly pour §b" + target.getName());
				} else {
					target.setAllowFlight(false);
					target.setFlying(false);
					sender.sendMessage("§6Vous avez §cdésactivé §6le fly pour §b" + target.getName());
				}
			} else {
				sender.sendMessage("§4[Erreur]: §c" + args[0] + " n'est pas connecté");
			}
		} else {
			sender.sendMessage("§cusage: /" + aliase + " [joueur]");

		}
		return true;
	}
}
