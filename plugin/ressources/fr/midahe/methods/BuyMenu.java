package fr.midahe.methods;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BuyMenu {

	public BuyMenu(Player player, ItemStack item, String name, String ancienMenu, int price) {

		Inventory inv = Bukkit.createInventory(null, 45, "§a§l[ACHAT]: §f" + name);

		inv.setItem(4, item);

		ItemStack redGlasses = new ItemStack(Material.RED_STAINED_GLASS_PANE);
		
		redGlasses = Methods.createItem(redGlasses, "§cSet to 1");
		inv.setItem(19, redGlasses);

		redGlasses.setAmount(10);
		redGlasses = Methods.createItem(redGlasses, "§cRemove 10");
		inv.setItem(20, redGlasses);

		redGlasses.setAmount(1);
		redGlasses = Methods.createItem(redGlasses, "§cRemove 1");
		inv.setItem(21, redGlasses);


		ItemStack greenGlasses = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
		
		greenGlasses = Methods.createItem(greenGlasses, "§aAdd 1");
		inv.setItem(23, greenGlasses);
		
		greenGlasses.setAmount(10);
		greenGlasses = Methods.createItem(greenGlasses, "§aAdd 10");
		inv.setItem(24, greenGlasses);
		
		greenGlasses.setAmount(64);
		greenGlasses = Methods.createItem(greenGlasses, "§aSet to 64");
		inv.setItem(25, greenGlasses);
		
		ItemStack validate = new ItemStack(Material.GREEN_STAINED_GLASS);
		validate = Methods.createItem(validate, "§a§lAcheter §7(" + price + " coins)");
		inv.setItem(41, validate);
		
		ItemStack back = new ItemStack(Material.RED_STAINED_GLASS);
		back = Methods.createItem(back, ancienMenu);
		inv.setItem(39, back);
		
		player.openInventory(inv);
	}
}
