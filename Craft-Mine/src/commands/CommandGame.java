package commands;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import instance.Time;
import main.Config;
import main.Main;
import main.Variables;
import ressources.Defi;

public class CommandGame implements CommandExecutor {

	Main main;
	BukkitTask task;

	public CommandGame(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String aliase, String[] args) {

		if (sender instanceof Player && !(sender.isOp())) {
			sender.sendMessage(Config.prohibitedCommand);
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage("§cUsage: /" + aliase + " [start|stop]");
			return true;
		}

		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("start")) {

				if (Variables.gameStarted) {
					Main.console.sendMessage("§cLe jeu §6Craft§3&§eMine §cest déjà §alancé");
					return true;
				}
				
				Bukkit.broadcastMessage("§cAttention, le jeu va commencer !");
				delayedStart();

				return true;
			} else if (args[0].equalsIgnoreCase("stop")) {

				if (!Variables.gameStarted) {
					sender.sendMessage("§cLe jeu §6Craft§3&§eMine §cest déjà §4arrété");
					return true;
				}

				for (Player players : Bukkit.getOnlinePlayers()) {
					players.teleport(Config.spawnArea);
				}
				Bukkit.broadcastMessage("§4Le jeu a été arrété par un modérateur");
				Variables.gameStarted = false;
				Time.running = false;
				return true;

			}else {
				sender.sendMessage("§cUsage: /" + aliase + " [start|stop]");
			}
		}

		return true;
	}

	private void delayedStart() {
		if (this.task != null)
			return;
		this.task = new BukkitRunnable() {

			int time = 5;

			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				for (Player players : Bukkit.getOnlinePlayers()) {
					players.sendTitle("§6Craft§3&§eMine", "§aDébut dans " + this.time + " secondes");
					Main.console.sendMessage("§cLe jeu commence dans §6" + this.time + " secondes");
				}
				this.time--;

				if (this.time < 0) {

					CommandGame.this.task.cancel();
					
					Main.console.sendMessage("§6Craft§3&§eMine §aest lance");
					CommandGame.this.task = null;
					Main.world.setTime(1000L);
					Variables.gameStarted = true;
					Variables.achivement = new HashMap<>();
					
					for (Player players : Bukkit.getOnlinePlayers()) {
						players.teleport(Config.startArea);
						players.sendTitle("§6Craft§3&§eMine", "§aLe jeu commence", 10, 20, 10);
						players.getInventory().clear();
						Variables.achivement.put(players.getUniqueId(), new ArrayList<Defi>());
						Variables.points.put(players.getUniqueId(), "0");
					}
					new Time();
					return;
				}
			}
		}.runTaskTimer(main, 0, 20);
	}

	@SuppressWarnings("deprecation")
	public static void endGame() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.teleport(Config.spawnArea);
			player.sendTitle("§6Craft§3&§eMine", "§cJeu fini");
		}
		Variables.gameStarted = false;
		Main.console.sendMessage("§6Craft§3&§eMine §cest fini");
	}
}