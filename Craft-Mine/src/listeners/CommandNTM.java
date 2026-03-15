package listeners;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import instance.ScoreBoard;

public class CommandNTM implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		ScoreBoard.obj.unregister();
		return false;
	}

}
