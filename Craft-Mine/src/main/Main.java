package main;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import commands.ChallengesTabCompleter;
import commands.CommandChallenges;
import commands.CommandGame;
import commands.CommandReloadConfig;
import commands.CommandTime;
import commands.GameTabCompleter;
import instance.ScoreBoard;
import instance.Time;
import listeners.BlockListener;
import listeners.CommandNTM;
import listeners.ConnectionListener;
import listeners.DeathEventListener;
import listeners.InventoryClick;

public class Main extends JavaPlugin {
		
	public static final ConsoleCommandSender console = Bukkit.getConsoleSender();
	private final PluginManager pm = Bukkit.getPluginManager();
	public static World world;
	
	
	@Override
	public void onLoad() {
		Variables.gameStarted = false;
		new Config(this);
		Main.world = Bukkit.getWorld(getConfig().getString("startArea.world"));
		
	}
	
	@Override
	public void onEnable(){
		console.sendMessage("§aLe plugin §6Craft§3&§eMine §aa ete §lactive");
		pm.registerEvents(new ConnectionListener(), this);
		pm.registerEvents(new BlockListener(), this);
		pm.registerEvents(new DeathEventListener(this), this);
		pm.registerEvents(new InventoryClick(), this);
		
		getCommand("game").setExecutor(new CommandGame(this));
		getCommand("game").setTabCompleter(new GameTabCompleter());
		getCommand("t").setExecutor(new CommandTime());
		
		getCommand("ntm").setExecutor(new CommandNTM());
		getCommand("reloadconfig").setExecutor(new CommandReloadConfig(this));
		
		getCommand("challenges").setExecutor(new CommandChallenges());
		getCommand("challenges").setTabCompleter(new ChallengesTabCompleter());
		
		console.sendMessage("§c[Fonctionnalite]: nom au dessus des joueurs en test !");
	}
	
	@Override
	public void onDisable() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.kickPlayer("§4Redémarage du serveur");
		}
		console.sendMessage("§aLe plugin §6Craft§3&§eMine §aa ete §l§cdesactive");
		Time.stopit();
		if(ScoreBoard.obj != null)
			ScoreBoard.obj.unregister();
	}
}
