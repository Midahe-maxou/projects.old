package ressources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Defi {

	StonePickaxe(1, 1, "§1Pickaxe", Material.STONE_PICKAXE, Arrays.asList("§6Crafter une §7pioche en pierre"),
			Arrays.asList(new ItemStack(Material.STONE_PICKAXE, 1))),
	Diamond(2, 3, "§bDiamonds", Material.DIAMOND, Arrays.asList("§6Miner un §bdiamant"),
			Arrays.asList(new ItemStack(Material.DIAMOND, 2), new ItemStack(Material.GOLD_INGOT, 5)));

	int number, level;
	String name;
	Material material;
	List<String> lore;
	List<ItemStack> require;

	Defi(int number, int level, String name, Material material, List<String> lore, List<ItemStack> require) {
		this.number = number;
		this.level = level;
		this.name = name;
		this.material = material;
		this.lore = lore;
		this.require = require;
	}

	public int getNumber() {
		return this.number;
	}

	public int getLevel() {
		return this.level;
	}

	public String getName() {
		return this.name;
	}

	public Material getMaterial() {
		return this.material;
	}

	public List<String> getLore() {
		return this.lore;
	}

	public List<ItemStack> getRequire() {
		return this.require;
	}

	public static List<Defi> getByLevel(int level) {
		List<Defi> defis = new ArrayList<Defi>();
		for (Defi defi : Defi.values()) {

			if (defi.getLevel() == level)
				defis.add(defi);
		}
		return defis;
	}

	public static Defi get(int number) {
		for (Defi defi : Defi.values()) {
			if (defi.getNumber() == number)
				return defi;
		}
		return null;
	}

	public static Defi get(String name) {
		for (Defi defi : Defi.values()) {
			if (defi.getName().equalsIgnoreCase(name))
				return defi;
		}
		return null;
	}
}
