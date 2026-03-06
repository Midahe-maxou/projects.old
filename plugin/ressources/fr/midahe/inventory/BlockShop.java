package fr.midahe.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.midahe.methods.Methods;

public class BlockShop {

	String buyLore = "§l§a[ACHETER]: §r§7Click droit";
	String sellLore = "§l§c[VENDRE]: §r§7Click gauche";

	public BlockShop(Player player) {

		player.closeInventory();

		Inventory inv = Bukkit.createInventory(null, 54, "§6§lShop > Block");

		ItemStack itStone = new ItemStack(Material.STONE);
		itStone = Methods.createItem(itStone, "§lStone §7(1 coins)", buyLore , sellLore);
		inv.setItem(10, itStone);

		ItemStack itGranite = new ItemStack(Material.GRANITE, 1);
		itGranite = Methods.createItem(itGranite, "§lGranite §7(1 coins)", buyLore, sellLore);
		inv.setItem(14, itGranite);

		ItemStack itPGranite = new ItemStack(Material.POLISHED_GRANITE, 1);
		itPGranite = Methods.createItem(itPGranite, "§lPolished Granite §7(1 coins)", buyLore, sellLore);
		inv.setItem(15, itPGranite);

		ItemStack itDiorite = new ItemStack(Material.DIORITE, 1);
		itDiorite = Methods.createItem(itDiorite, "§lDiorite §7(1 coins)", buyLore, sellLore);
		inv.setItem(16, itDiorite);

		ItemStack itPDiorite = new ItemStack(Material.POLISHED_DIORITE, 1);
		itPDiorite = Methods.createItem(itPDiorite, "§lPolished Diorite §7(1 coins)", buyLore, sellLore);
		inv.setItem(23, itPDiorite);

		ItemStack itAndesite = new ItemStack(Material.ANDESITE, 1);
		itAndesite = Methods.createItem(itAndesite, "§lAndesite §7(1 coins)", buyLore, sellLore);
		inv.setItem(24, itAndesite);

		ItemStack itPAndesite = new ItemStack(Material.POLISHED_ANDESITE, 1);
		itPAndesite = Methods.createItem(itPAndesite, "§lPolished Andesite §7(1 coins)", buyLore, sellLore);
		inv.setItem(25, itPAndesite);

		ItemStack itGrass = new ItemStack(Material.GRASS_BLOCK);
		itGrass = Methods.createItem(itGrass, "§lGrass §7(10 coins)", buyLore, sellLore);
		inv.setItem(11, itGrass);

		ItemStack itDirt = new ItemStack(Material.DIRT);
		itDirt = Methods.createItem(itDirt, "§lDirt §7(5 coins)", buyLore, sellLore);
		inv.setItem(12, itDirt);

		ItemStack itCobble = new ItemStack(Material.COBBLESTONE);
		itCobble = Methods.createItem(itCobble, "§lCobblestone §7(1 coins)", buyLore, sellLore);
		inv.setItem(19, itCobble);

		ItemStack itMCobble = new ItemStack(Material.MOSSY_COBBLESTONE);
		itMCobble = Methods.createItem(itMCobble, "§lMossy Cobblestone §7(10 coins)", buyLore, sellLore);
		inv.setItem(20, itMCobble);

		ItemStack itStoneBrick = new ItemStack(Material.BRICK);
		itStoneBrick = Methods.createItem(itStoneBrick, "§lStone Brick §7(10 coins)", buyLore, sellLore);
		inv.setItem(21, itStoneBrick);

		ItemStack itMStoneBrick = new ItemStack(Material.MOSSY_STONE_BRICKS, 1);
		itMStoneBrick = Methods.createItem(itMStoneBrick, "§lMossy Stone Brick §7(10 coins)", buyLore, sellLore);
		inv.setItem(28, itMStoneBrick);

		ItemStack itCrStoneBrick = new ItemStack(Material.CRACKED_STONE_BRICKS, 1);
		itCrStoneBrick = Methods.createItem(itCrStoneBrick, "§lCracked Stone Brick §7(10 coins)", buyLore, sellLore);
		inv.setItem(29, itCrStoneBrick);

		ItemStack itChStoneBrick = new ItemStack(Material.CHISELED_STONE_BRICKS, 1);
		itChStoneBrick = Methods.createItem(itChStoneBrick, "§lChiseled Stone Brick §7(10 coins)", buyLore, sellLore);
		inv.setItem(30, itChStoneBrick);

		ItemStack itMyceluim = new ItemStack(Material.MYCELIUM);
		itMyceluim = Methods.createItem(itMyceluim, "§lMyceluim §7(500 coins)", buyLore, sellLore);
		inv.setItem(32, itMyceluim);

		ItemStack itPodzol = new ItemStack(Material.PODZOL, 1);
		itPodzol = Methods.createItem(itPodzol, "§lPodzol §7(200 coins)", buyLore, sellLore);
		inv.setItem(33, itPodzol);

		ItemStack itCDirt = new ItemStack(Material.COARSE_DIRT, 1);
		itCDirt = Methods.createItem(itCDirt, "§lCoarse Dirt §7(50 coins)", buyLore, sellLore);
		inv.setItem(34, itCDirt);

		ItemStack back = new ItemStack(Material.ENDER_EYE);
		back = Methods.createItem(back, "§c§lBack", "§aRevenir au Shop principale");
		inv.setItem(49, back);

		ItemStack nextPage = new ItemStack(Material.PAPER);
		nextPage = Methods.createItem(nextPage, "§e§lPage suivante");
		inv.setItem(53, nextPage);

		player.openInventory(inv);

	}
}
