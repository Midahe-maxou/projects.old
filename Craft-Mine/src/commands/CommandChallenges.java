package commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import main.Config;
import main.Main;
import main.Variables;
import ressources.Defi;

public class CommandChallenges implements CommandExecutor {

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String aliase, String[] args) {
		
		if(!Variables.gameStarted) {
			sender.sendMessage(Config.prohibitedCommand);
			return true;
		}
		
		if(sender instanceof Player) {
			
			Player player = (Player)sender;
			
			if(args.length == 0) {
				Inventory inv = Bukkit.createInventory(null, 27, "§6Liste des Défis");
				
				for(Defi defi : Defi.values()) {
					
					ItemStack item = new ItemStack(defi.getMaterial());
					
					ItemMeta meta = item.getItemMeta();
					
					if(Variables.achivement.get(player.getUniqueId()) != null && Variables.achivement.get(player.getUniqueId()).contains(defi)) {
						meta.setDisplayName("§cDéfi n°" + defi.getNumber());
						meta.setLore(Arrays.asList("§c§k..§cDéfi déjà réalisé§k.."));
						item.setType(Material.BARRIER);
					}else {
						meta.setDisplayName("§aDéfi n°" + defi.getNumber());
						meta.setLore(defi.getLore());
					}
					
					meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
					meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
					meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
					
					item.setItemMeta(meta);
					inv.setItem(defi.getNumber()-1, item);
				}
				player.openInventory(inv);
			}
		}else {
			Main.console.sendMessage("§cVous n'etes pas un joueur");
		}
		return true;
	}
}
