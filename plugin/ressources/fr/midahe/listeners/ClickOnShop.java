package fr.midahe.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.midahe.inventory.BlockShop;
import fr.midahe.inventory.MainShop;
import fr.midahe.methods.BuyMenu;
import fr.midahe.sqlConnection.SqlMethods;

public class ClickOnShop implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		
		ItemStack current = (ItemStack) e.getCurrentItem();
		Player player = (Player) e.getWhoClicked();
		
		SqlMethods sql = new SqlMethods();
		
		//if(inv.toString().equals("§r§6§lShop")) {
			
			if (current != null) {
				
				ItemMeta it = current.getItemMeta();
				
				InventoryView invView = player.getOpenInventory();
				if (invView.getTitle().equals("§6§lShop")) {
					
					e.setCancelled(true);
					
					switch(current.getType()) {
					
					case GRASS_BLOCK:
						new BlockShop(player);
						break;
						
/************************ON DEVLOPPMENT************************/				
/*					case DIAMOND:
*						new OreShop(player);
*						player.closeInventory();
*						break;
*						
*					case DIAMOND_PICKAXE:
*						new ToolsShop(player);
*						player.closeInventory();
*						break;
*						
*					case RAW_FISH:
*						new MiscellaneousShop(player);
*						player.closeInventory();
*						break;
*						
*					case DIAMOND_SWORD:
*						new PvpShop(player);
*						player.closeInventory();
*						break;
*						
*					case MOB_SPAWNER:
*						new SpawnerShop(player);
*						player.closeInventory();
*						break;
*						
*					case REDSTONE:
*						new RedstoneShop(player);
*						player.closeInventory();
*						break;
*						
*					case BOOK:
*						new EnchantmentShop(player);
*						player.closeInventory();
*						break;
*						
*/					
					default: 
						break;
				}
			}if(invView.getTitle().equals("§6§lShop > Block")) {
				
				e.setCancelled(true);
				it.setLore(null);
				current.setItemMeta(it);
				
				if(it.getDisplayName().equals("§c§lBack")) {
					new MainShop(player);
					return;
				}if(it.getDisplayName().equals("§e§lPage suivante")) {
					
					return;
				}
				
				String name = it.getDisplayName();
				String[] str = name.split("[(]");
				str[1] = str[1].replace(" coins", "");
				str[1] = str[1].replace(")", "");
				int price = Integer.valueOf(str[1]);
					
				new BuyMenu(player, current, current.getItemMeta().getDisplayName(), invView.getTitle(), price);
				
			}if(invView.getTitle().contains("§a§l[ACHAT]:")) {
				
				e.setCancelled(true);
				
				ItemStack boughtBlock = invView.getItem(4);
				ItemMeta bMeta = boughtBlock.getItemMeta();
				String name = bMeta.getDisplayName();
				String[] str = name.split("[(]");
				str[1] = str[1].replace(" coins", "");
				str[1] = str[1].replace(")", "");
				int price = Integer.valueOf(str[1]);
				
				ItemStack validateBlock = invView.getItem(41);
				ItemMeta vMeta = validateBlock.getItemMeta();
				String name2 = vMeta.getDisplayName();
				String[] str2 = name2.split("[(]");
				str2[1] = str2[1].replace(" coins", "");
				str2[1] = str2[1].replace(")", "");
				int currentPrice = Integer.valueOf(str2[1]);
				
				switch(it.getDisplayName()) {
				
				case "§cSet to 1":
					boughtBlock.setAmount(1);
					vMeta.setDisplayName("§a§lAcheter §7(" + price + " coins)");
					break;
					
				case "§cRemove 10":
					if(boughtBlock.getAmount() > 9) {
						boughtBlock.setAmount(boughtBlock.getAmount() - 10);
						vMeta.setDisplayName("§a§lAcheter §7(" + (currentPrice - (price*10) + " coins)"));
					}else {
						boughtBlock.setAmount(1);
						vMeta.setDisplayName("§a§lAcheter §7(" + price + " coins)");
					}
					break;
				
				case "§cRemove 1":
					if(boughtBlock.getAmount() > 1) {
						boughtBlock.setAmount(boughtBlock.getAmount() - 1);
						vMeta.setDisplayName("§a§lAcheter §7(" + (currentPrice - price + " coins)"));
					break;
					}
					
				case "§aAdd 1":
					if(boughtBlock.getAmount() < 64) {
						boughtBlock.setAmount(boughtBlock.getAmount() + 1);
						vMeta.setDisplayName("§a§lAcheter §7(" + (currentPrice + price) + " coins)");
					}
					break;
					
				case "§aAdd 10":
					if(boughtBlock.getAmount() < 54) {
						boughtBlock.setAmount(boughtBlock.getAmount() + 10);
						vMeta.setDisplayName("§a§lAcheter §7(" + (currentPrice + (price*10)) + " coins)");
					}else {
						boughtBlock.setAmount(64);
						vMeta.setDisplayName("§a§lAcheter §7(" + (price*64) + " coins)");
					}
					break;
					
				case "§aSet to 64":
					boughtBlock.setAmount(64);
					vMeta.setDisplayName("§a§lAcheter §7(" + (price*64) + " coins)");
					break;
					
				default:
					
					if(it.getDisplayName().contains("§a§lAcheter")) {
						if(sql.getBalance(player) >= currentPrice) {
							boughtBlock.setAmount(currentPrice/price);
							player.getInventory().addItem(boughtBlock);
							sql.removeMoney(player, currentPrice);
							player.sendMessage("§6Vous avez bien §aacheté §3" + (currentPrice/price) + " " + boughtBlock.getItemMeta().getDisplayName() + " §6pour §5" + currentPrice + " coins");
							player.closeInventory();
						}else {
							player.sendMessage("§4[Erreur]: §cVous n'avez pas assez d'argent");
							player.closeInventory();
							break;
						}
					}if(it.getDisplayName().equals("§6§lShop > Block")) {
						new BlockShop(player);
						break;
					}
				}
				validateBlock.setItemMeta(vMeta);
			}
		}
	}
}
