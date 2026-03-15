package main;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public final class Config {
	
	public static Location spawnArea;// = new Location(Main.world, -204.5, 120, -125.5);
	public static Location startArea;// = new Location(Main.world, -204.5, 64, -125.5);
	
	public static String prohibitedCommand;// = "§4[Erreur]: §cVous n'avez pas la permission de faire cette commande";
	
	public static String time;
	
	public static int pt1, pt2, pt3;
	
	
	public Config(Main main) {
		
		FileConfiguration config = main.getConfig();
		
		Config.spawnArea = new Location(Bukkit.getWorld(config.getString("spawnArea.world")), config.getInt("spawnArea.x"), config.getInt("spawnArea.y"), config.getInt("spawnArea.z"));
		Config.startArea = new Location(Bukkit.getWorld(config.getString("startArea.world")), config.getInt("startArea.x"), config.getInt("startArea.y"), config.getInt("startArea.z"));
		
		Config.prohibitedCommand = config.getString("prohibitedCommand").replace("&", "§");
		
		time = config.getString("time");
		
		Config.pt1 = config.getInt("points.lvl1");
		Config.pt2 = config.getInt("points.lvl2");
		Config.pt3 = config.getInt("points.lvl3");
		
		Bukkit.broadcastMessage("" + pt1 + pt2 + pt3);
		
		Variables.achivement = new HashMap<>();
		Variables.points = new HashMap<>();
	}
	
	public static int getPtByLevel(int level) {
		if(level == 1) return pt1;
		if(level == 2) return pt2;
		if(level == 3) return pt3;
		return 0;
	}
}