package fr.midahe.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.midahe.methods.Methods;

public class MainShop {

	int i;

	public MainShop(Player player) {

		Inventory inv = Bukkit.createInventory(null, 45, "§6§lShop");
		player.closeInventory();

		ItemStack itBlueGlasses = new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1);
		itBlueGlasses = Methods.createItem(itBlueGlasses, "§a");
		for (i = 0; i < 9; i++) {
			inv.setItem(i, itBlueGlasses);
		}
		for (i = 18; i < 27; i++) {
			inv.setItem(i, itBlueGlasses);
		}
		for (i = 36; i < 45; i++) {
			inv.setItem(i, itBlueGlasses);
		}
		inv.setItem(9, itBlueGlasses);
		inv.setItem(17, itBlueGlasses);
		inv.setItem(27, itBlueGlasses);
		inv.setItem(35, itBlueGlasses);

		ItemStack itRedGlasses = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
		itRedGlasses = Methods.createItem(itRedGlasses, "§a");
		inv.setItem(11, itRedGlasses);
		inv.setItem(13, itRedGlasses);
		inv.setItem(15, itRedGlasses);

		inv.setItem(29, itRedGlasses);
		inv.setItem(31, itRedGlasses);
		inv.setItem(33, itRedGlasses);

		ItemStack itGrass = new ItemStack(Material.GRASS_BLOCK);
		itGrass = Methods.createItem(itGrass, "§r§l§aBlock");
		inv.setItem(10, itGrass);

		ItemStack itDiamond = new ItemStack(Material.DIAMOND);
		itDiamond = Methods.createItem(itDiamond, "§r§l§bMinerais");
		inv.setItem(12, itDiamond);

		ItemStack itPickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
		itPickaxe = Methods.createItem(itPickaxe, "§r§l§9Outils", true);
		inv.setItem(14, itPickaxe);

		ItemStack itDivers = new ItemStack(Material.PUFFERFISH, 1);
		itDivers = Methods.createItem(itDivers, "§r§l§eDivers");
		inv.setItem(16, itDivers);

		ItemStack itSword = new ItemStack(Material.DIAMOND_SWORD);
		itSword = Methods.createItem(itSword, "§r§l§4PvP", true);
		inv.setItem(28, itSword);

		ItemStack itSpawner = new ItemStack(Material.SPAWNER);
		itSpawner = Methods.createItem(itSpawner, "§r§l§6Spawners");
		inv.setItem(30, itSpawner);

		ItemStack itRedstone = new ItemStack(Material.REDSTONE);
		itRedstone = Methods.createItem(itRedstone, "§r§l§cRedstone");
		inv.setItem(32, itRedstone);

		ItemStack itBook = new ItemStack(Material.BOOK);
		itBook = Methods.createItem(itBook, "§r§l§dEnchantements");
		inv.setItem(34, itBook);

		player.openInventory(inv);

	}
}
