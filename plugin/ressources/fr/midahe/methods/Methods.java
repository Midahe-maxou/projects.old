package fr.midahe.methods;

import java.util.ArrayList;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Methods {

	static String[] str = { "" };
	static ArrayList<String> desc = new ArrayList<>();

	public static ItemStack createItem(ItemStack it, String customName, boolean isEnchanting, String... lore) {

		ItemMeta itM = it.getItemMeta();

		for (String part : lore) {
			desc.add(part);
		}

		itM.setDisplayName(customName);
		itM.setLore(desc);

		desc.clear();

		if (isEnchanting) {
			itM.addEnchant(Enchantment.LUCK, 1, true);
		}
		itM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		it.setItemMeta(itM);
		return it;
	}

	public static ItemStack createItem(ItemStack it, String customName, String... lore) {
		return createItem(it, customName, false, lore);
	}

	public static ItemStack createItem(ItemStack it, String customName, boolean isEnchanting) {

		ItemMeta itM = it.getItemMeta();
		itM.setDisplayName(customName);

		if (isEnchanting) {
			itM.addEnchant(Enchantment.LUCK, 1, true);
		}
		itM.addItemFlags(ItemFlag.HIDE_ENCHANTS);

		it.setItemMeta(itM);
		return it;
	}

	public static ItemStack createItem(ItemStack it, String customName) {
		return createItem(it, customName, false);
	}

}