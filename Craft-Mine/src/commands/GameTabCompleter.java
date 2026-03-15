package commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

public class GameTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String aliase, String[] args) {
		if(args.length > 1)return Arrays.asList("");
		
		List<String> list = Arrays.asList("start", "stop");
		List<String> arg = new ArrayList<>();
		
		StringUtil.copyPartialMatches(args[0], list, arg);
		Collections.sort(arg);
		
		return arg;
	}
}
