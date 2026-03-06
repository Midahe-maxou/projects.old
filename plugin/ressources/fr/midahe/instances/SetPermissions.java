package fr.midahe.instances;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import fr.midahe.main.Main;

public class SetPermissions {

	public static List<String> permissions = new ArrayList<>();
	public static List<String> apprentiPermission = new ArrayList<>();
	public static List<String> sorcierPermission = new ArrayList<>();
	public static List<String> magePermission = new ArrayList<>();
	public static List<String> devinPermission = new ArrayList<>();
	public static List<String> necromancienPermission = new ArrayList<>();
	public static List<String> helpeurPermission = new ArrayList<>();
	public static List<String> modoPermission = new ArrayList<>();
	public static List<String> supermodoPermission = new ArrayList<>();
	public static List<String> adminPermission = new ArrayList<>();

	public SetPermissions(Main main) {

		FileConfiguration config = main.getConfig();

		for (String perm : config.getStringList("permissions.apprenti")) {

			permissions.add(perm);
			apprentiPermission.add(perm);
			sorcierPermission.add(perm);
			magePermission.add(perm);
			devinPermission.add(perm);
			necromancienPermission.add(perm);
			helpeurPermission.add(perm);
			modoPermission.add(perm);
			supermodoPermission.add(perm);
			adminPermission.add(perm);
		}

		for (String perm : config.getStringList("permissions.sorcier")) {

			permissions.add(perm);
			sorcierPermission.add(perm);
			magePermission.add(perm);
			devinPermission.add(perm);
			necromancienPermission.add(perm);
			helpeurPermission.add(perm);
			modoPermission.add(perm);
			supermodoPermission.add(perm);
			adminPermission.add(perm);
		}

		for (String perm : config.getStringList("permissions.mage")) {

			permissions.add(perm);
			magePermission.add(perm);
			devinPermission.add(perm);
			necromancienPermission.add(perm);
			helpeurPermission.add(perm);
			modoPermission.add(perm);
			supermodoPermission.add(perm);
			adminPermission.add(perm);
		}

		for (String perm : config.getStringList("permissions.devin")) {

			permissions.add(perm);
			devinPermission.add(perm);
			necromancienPermission.add(perm);
			helpeurPermission.add(perm);
			modoPermission.add(perm);
			supermodoPermission.add(perm);
			adminPermission.add(perm);
		}

		for (String perm : config.getStringList("permissions.necromancien")) {

			permissions.add(perm);
			necromancienPermission.add(perm);
			helpeurPermission.add(perm);
			modoPermission.add(perm);
			supermodoPermission.add(perm);
			adminPermission.add(perm);
		}

		for (String perm : config.getStringList("permissions.helpeur")) {

			permissions.add(perm);
			helpeurPermission.add(perm);
			modoPermission.add(perm);
			supermodoPermission.add(perm);
			adminPermission.add(perm);
		}

		for (String perm : config.getStringList("permissions.modo")) {

			permissions.add(perm);
			modoPermission.add(perm);
			supermodoPermission.add(perm);
			adminPermission.add(perm);
		}

		for (String perm : config.getStringList("permissions.supermodo")) {

			permissions.add(perm);
			supermodoPermission.add(perm);
			adminPermission.add(perm);
		}

		for (String perm : config.getStringList("permissions.admin")) {

			permissions.add(perm);
			adminPermission.add(perm);
		}
	}
}
