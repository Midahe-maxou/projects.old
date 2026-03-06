package fr.midahe.tabCompleters;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class MobTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String aliase, String[] args) {
		
		List<String> list = new ArrayList<>();
		String rawList;
		
		
		if(args.length == 1) {
			rawList = "kill,effect,tp";
			
			for(String part : rawList.split(",")) {
				list.add(part);
			}
		}if(args.length == 2) {
			if(args[1].equalsIgnoreCase("kill")) {
				list.add("");
				return list;
			}
			
			if(args[1].equalsIgnoreCase("effect")) {
				rawList = "Absorption,Blindness,Fire_resistance,Haste,Health_boost,Hunger,Invisibility,Jump_boost,Mining_fatigue,Nausea,Night_vision,Slowness,Speed,Strengh,Water_breathing,Weakness,Wither";
				
				for(String part : rawList.split(",")) {
					list.add(part);
				}
				
				if(args.length == 3 || args.length == 4) {
					list.add("1");
				}
				
				if(args.length == 5) {
					list.add("true");
				}
			}if(args[1].equalsIgnoreCase("tp")) {
				return null;
			}
		}
		return list;
	}
}
