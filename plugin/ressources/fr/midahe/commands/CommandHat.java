package fr.midahe.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandHat implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String aliase, String[] args) {

		if (sender instanceof Player) {

			if (!sender.hasPermission("command.hat")) {
				sender.sendMessage("§4[Erreur]: §cvous n'avez pas la permission");
				return true;
			}

			Player player = (Player) sender;
			
			player.getInventory().getItemInHand();

			if (player.getInventory().getItemInHand().getType().toString().equalsIgnoreCase("air") == false) {
				if (player.getInventory().getHelmet() == null) {
					ItemStack item = player.getInventory().getItemInHand();
					player.getInventory().setHelmet(item);
					player.getInventory().setItemInHand(null);
					player.sendMessage("§bVous venez de mettre un §e" + item.getType().toString().toLowerCase()
							+ " §bComme chapeau");
				} else {
					player.sendMessage("§4[Erreur]: §cVous avez déjà un chapeau");
				}
			} else {
				player.sendMessage("§4[Erreur]: §cVous n'avez rien dans votre main");
				return true;
			}
		} else {
			sender.sendMessage(
					"§4[Erreur]: §cVous n'avez pas de tete, donc vous ne pouvez pas" + '\n' + " avoir de chapeau");
		}
		return true;
	}
}
