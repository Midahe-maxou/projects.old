package fr.midahe.main;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.midahe.commands.CommandAddMoney;
import fr.midahe.commands.CommandClearlag;
import fr.midahe.commands.CommandDay;
import fr.midahe.commands.CommandFeed;
import fr.midahe.commands.CommandFly;
import fr.midahe.commands.CommandHat;
import fr.midahe.commands.CommandHeal;
import fr.midahe.commands.CommandMlg;
import fr.midahe.commands.CommandMob;
import fr.midahe.commands.CommandMoney;
import fr.midahe.commands.CommandNight;
import fr.midahe.commands.CommandPay;
import fr.midahe.commands.CommandPermission;
import fr.midahe.commands.CommandPlayer;
import fr.midahe.commands.CommandRemoveMoney;
import fr.midahe.commands.CommandSetgrade;
import fr.midahe.commands.CommandShop;
import fr.midahe.instances.SetPermissions;
import fr.midahe.listeners.ClickOnShop;
import fr.midahe.listeners.DamageListener;
import fr.midahe.listeners.KillMobListener;
import fr.midahe.listeners.PlayerTakeDamagelistener;
import fr.midahe.listeners.PvpClickEvent;
import fr.midahe.listeners.SetPermOnJoin;
import fr.midahe.listeners.SpawnMobListener;
import fr.midahe.listeners.SqlListeners;
import fr.midahe.listeners.VelocityListener;
import fr.midahe.sqlConnection.SqlConnection;
import fr.midahe.tabCompleters.MobTabCompleter;
import fr.midahe.tabCompleters.MoneyTabcompleter;
import fr.midahe.tabCompleters.PermissionTabCompleter;
import fr.midahe.tabCompleters.PlayerTabCompleter;
import fr.midahe.tabCompleters.setgradeTabCompleter;
import fr.midahe.time.Time;

public class Main extends JavaPlugin {

	public static SqlConnection sql = new SqlConnection("jdbc:mysql://", "localhost", "server18", "root", "");

	public static final ConsoleCommandSender console = Bukkit.getConsoleSender();

	public void onEnable() {
		console.sendMessage("§aLe plugin s'allume");

		sql.connect();

		File file = new File(getDataFolder() + File.separator + "config.yml");

		if (file.exists())
			file.delete();

		saveDefaultConfig();

		new Time();
		new SetPermissions(this);

		getCommand("day").setExecutor(new CommandDay());
		getCommand("night").setExecutor(new CommandNight());
		getCommand("clearlag").setExecutor(new CommandClearlag());
		getCommand("player").setExecutor(new CommandPlayer());
		getCommand("creatures").setExecutor(new CommandMob());
		getCommand("hat").setExecutor(new CommandHat());
		getCommand("fly").setExecutor(new CommandFly());
		getCommand("majorleaguegaming").setExecutor(new CommandMlg());
		getCommand("permission").setExecutor(new CommandPermission());
		getCommand("heal").setExecutor(new CommandHeal());
		getCommand("feed").setExecutor(new CommandFeed());
		getCommand("balance").setExecutor(new CommandMoney());
		getCommand("pay").setExecutor(new CommandPay());
		getCommand("addmoney").setExecutor(new CommandAddMoney());
		getCommand("removemoney").setExecutor(new CommandRemoveMoney());
		getCommand("setgrade").setExecutor(new CommandSetgrade());

		getCommand("player").setTabCompleter(new PlayerTabCompleter());
		getCommand("creatures").setTabCompleter(new MobTabCompleter());
		getCommand("permission").setTabCompleter(new PermissionTabCompleter());
		getCommand("pay").setTabCompleter(new MoneyTabcompleter());
		getCommand("addmoney").setTabCompleter(new MoneyTabcompleter());
		getCommand("removemoney").setTabCompleter(new MoneyTabcompleter());
		getCommand("setgrade").setTabCompleter(new setgradeTabCompleter());

		Bukkit.getPluginManager().registerEvents(new SpawnMobListener(), this);
		Bukkit.getPluginManager().registerEvents(new KillMobListener(), this);
		Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
		Bukkit.getPluginManager().registerEvents(new SetPermOnJoin(this), this);
		Bukkit.getPluginManager().registerEvents(new SqlListeners(), this);
		Bukkit.getPluginManager().registerEvents(new VelocityListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerTakeDamagelistener(), this);

		/**************************** ON DEVLOPPEMENT ****************************/
		getCommand("shop").setExecutor(new CommandShop());

		Bukkit.getPluginManager().registerEvents(new ClickOnShop(), this);
		Bukkit.getPluginManager().registerEvents(new PvpClickEvent(), this);

	}

	public void onDisable() {
		
		Bukkit.getWorld("world").setTime(2000);

		Time.stopit();

		for (Player player : Bukkit.getOnlinePlayers()) {
			player.kickPlayer(ChatColor.DARK_RED + "[SERVER]: " + ChatColor.RED + "Restart");
		}

		sql.disconnect();

		console.sendMessage("§cle plugin s'eteint");
	}
}