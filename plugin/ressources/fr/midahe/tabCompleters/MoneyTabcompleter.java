package fr.midahe.tabCompleters;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class MoneyTabcompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String aliase, String[] args) {
		
		List<String> list = new ArrayList<>();
		
		if(args.length == 2) {
			list.add("100");
			return list;
		}else if(args.length > 2) {
			list.add("");
			return list;
		}
		return null;
	}
}
