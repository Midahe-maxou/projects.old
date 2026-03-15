package listeners;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import main.Config;
import main.Variables;
import ressources.Defi;

public class InventoryClick implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		
		HumanEntity player = e.getWhoClicked();
		Inventory inv = e.getClickedInventory();
		Inventory pInv = player.getInventory();
		
		if (inv.getTitle().equals("§6Liste des Défis")) {
			e.setCancelled(true);
			
			if(e.getSlot() != -999) {
				Defi defi = Defi.get(e.getSlot()+1); //slots begin to 0
				
				if(defi == null || e.getCurrentItem().getType() == Material.BARRIER) return;
				
				for(ItemStack item : defi.getRequire()) {
					if(!(pInv.containsAtLeast(item, item.getAmount()))) {
						player.sendMessage("§cVous n'avez pas les ressources nécéssaires pour compléter ce défi §b(" + item.getAmount() + " " + item.getType().name() +")");
						player.closeInventory();
						return;
					}
				}
				
				for(ItemStack item : defi.getRequire()) {
					
					int price = item.getAmount();
						
					w:while(price > 0) {
						int slot = pInv.first(item.getType()); //-1 == no item in inv
						if(slot == -1) return;
						ItemStack it = pInv.getItem(slot);
						if(it.getAmount() >= price) {
							it.setAmount(it.getAmount()-price);
							break w;
						}else{
							price -= it.getAmount();
							it.setAmount(0);
						}
					}
				}
				
				if(Variables.achivement.get(player.getUniqueId()) != null) {
					List<Defi> list = Variables.achivement.get(player.getUniqueId());
					list.add(defi);
					Variables.achivement.put(player.getUniqueId(), list);
				}else {
					Variables.achivement.put(player.getUniqueId(), Arrays.asList(defi));
				}
				player.closeInventory();
				Variables.points.put(player.getUniqueId(), (Integer.valueOf(Variables.points.get(player.getUniqueId())) + Config.getPtByLevel(defi.getLevel())) + "");
				
				Bukkit.broadcastMessage("§b" + player.getName() + " §evient de faire le défi n°" + defi.getNumber() + " §7(" + defi.getName() +"§7)" + '\n'
										 + "§e il est maintenant à §a" + Variables.points.get(player.getUniqueId()) + " points");
				((Player) player).sendTitle("§aDéfi réussi", "§bVous avez réussi le défi n°" + defi.getNumber());
			}
		}
	}
}
