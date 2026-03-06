package fr.midahe.tabCompleters;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import fr.midahe.instances.SetPermissions;

public class PermissionTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String aliase, String[] args) {
		
		List<String> list = new ArrayList<>();
		String[] currentWord;
		String rawList;
		
		if(args.length == 1) return null;
		
		if(args.length == 2) {
			
			for(String part : SetPermissions.permissions) list.add(part);
		}if(args.length == 3) {
			currentWord = args[2].split("");
			rawList = "true,false";
			if(currentWord.length > 0) {
				if(currentWord[0].equalsIgnoreCase("t")) {
					rawList = "true";
				}else if(currentWord[0].equalsIgnoreCase("f")) {
					rawList = "false";
				}else {
					rawList = "";
				}
			}
			for(String part : rawList.split(",")) list.add(part);
		}if(args.length > 3) list.add("");
		return list;
	}
}
