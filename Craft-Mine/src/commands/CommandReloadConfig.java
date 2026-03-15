package commands;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.Config;
import main.Main;

public class CommandReloadConfig implements CommandExecutor {

	Main main;
	
	public CommandReloadConfig(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String aliase, String[] args) {
		
		if(sender instanceof Player && !(sender.isOp())) {
			sender.sendMessage(Config.prohibitedCommand);
			return true;
		}
		File file = new File(this.main.getDataFolder() + File.separator + "config.yml");
		if(file.exists()) file.delete();
		this.main.saveDefaultConfig();
		
		if(sender instanceof Player)sender.sendMessage("§4Config.yml §cà été reconfiguré comme défaut");
		else sender.sendMessage("§4Config.yml §ca ete reconfigure comme defaut");
		
		return true;
	}
}
