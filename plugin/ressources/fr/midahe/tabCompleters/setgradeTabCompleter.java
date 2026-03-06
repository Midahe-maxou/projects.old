package fr.midahe.tabCompleters;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class setgradeTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String aliase, String[] args) {

		List<String> list = new ArrayList<>(), nullList = new ArrayList<>();
		String rawList = "";
		nullList.add("");

		if (args.length == 1) {
			return null;
		}

		if (args.length == 2) {
			rawList = "apprenti,sorcier,mage,devin,necromancien,helpeur,modo,supermodo,admin";
			for (String part : rawList.split(",")) {
				list.add(part);
			}
			return list;
		}

		return nullList;
	}
}
