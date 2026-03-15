package commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class ChallengesTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String aliase, String[] args) {
		if(sender instanceof Player && !sender.isOp()) return Arrays.asList("");
		
		return null;
	}
}
