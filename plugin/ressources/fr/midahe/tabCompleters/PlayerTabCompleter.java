package fr.midahe.tabCompleters;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class PlayerTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String aliase, String[] args) {

		List<String> playerList = new ArrayList<>();
		String[] currentWord, acpc;

		for (Player p : Bukkit.getWorld("world").getPlayers()) {
			playerList.add(p.getName());
		}
		playerList.add("@");

		List<String> list = new ArrayList<>();
		String rawList;

		if (args.length == 1)
			return playerList;

		if (args.length == 2) {
			rawList = "kill,effect,tp,clear";
			for (String part : rawList.split(",")) {
				list.add(part);
			}
		}

		if (args[1].equalsIgnoreCase("kill") || args[1].equalsIgnoreCase("clear")) {
			list.add("");
			return list;

		}
		if (args[1].equalsIgnoreCase("effect")) {

			if (args.length == 3) {
				rawList = "Absorption,Blindness,Fire_resistance,Haste,Health_boost,Hunger,Invisibility,Jump_boost,Mining_fatigue,Nausea,Night_vision,Regeneration,Resistance,Slowness,Speed,Strengh,Water_breathing,Weakness,Wither,clear";

				currentWord = args[2].split("");

				for (String part : rawList.split(",")) {
					int j = 0;
					acpc = part.split("");
					
					
					for (int i = 0; i < currentWord.length; i++) {
						if (currentWord[i].equalsIgnoreCase(acpc[i])) {
							j++;
							sender.sendMessage("§cLetter" + (i+1) + " corenpond §b(" + acpc[i] + " = " + currentWord[i]);
						}
					}
					if (j == currentWord.length) {
						list.add(part);
						return list;
					}
				}

				for (String part : rawList.split(",")) {
					list.add(part);
				}

			}
			if (args.length == 4 || args.length == 5) {
				list.add("1");
			}
			if (args.length == 6) {
				list.add("true");
			}
		}
		if (args[1].equalsIgnoreCase("tp")) {
			if (args.length == 4 && args[2].equals("~")) {
				list.add("~");
			} else if (args.length == 5 && args[3].equals("~")) {
				list.add("~");
			} else {
				return null;
			}
		}
		return list;
	}
}