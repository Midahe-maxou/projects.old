package fr.midahe.listeners;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;

import fr.midahe.instances.SetPermissions;
import fr.midahe.main.Main;
import fr.midahe.sqlConnection.SqlMethods;

public class SetPermOnJoin implements Listener {

	public static HashMap<UUID, PermissionAttachment> permi = new HashMap<>();
	public static PermissionAttachment permAttachment;
	Plugin main;

	public SetPermOnJoin(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Player player = e.getPlayer();
		SqlMethods sql = new SqlMethods();
		int grade = sql.getGradeValue(player);
		
		player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).addModifier(new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", 24.000D, AttributeModifier.Operation.ADD_SCALAR));
		
		permAttachment = player.addAttachment(main);
		permi.put(player.getUniqueId(), permAttachment);
		
		if (player.hasPermission("op")) {
			
			for (String perm : SetPermissions.permissions) {
				permi.get(player.getUniqueId()).setPermission(perm, true);
			}
		}

		switch (grade) {
		case 1:
			for (String perm : SetPermissions.apprentiPermission) {
				permi.get(player.getUniqueId()).setPermission(perm, true);
			}
			player.setDisplayName("§r[Apprenti] " + player.getName());
			return;

		case 2:
			for (String perm : SetPermissions.magePermission) {
				permi.get(player.getUniqueId()).setPermission(perm, true);
			}
			return;

		case 3:
			for (String perm : SetPermissions.sorcierPermission) {
				permi.get(player.getUniqueId()).setPermission(perm, true);
			}
			return;

		case 4:
			for (String perm : SetPermissions.devinPermission) {
				permi.get(player.getUniqueId()).setPermission(perm, true);
			}
			return;

		case 5:
			for (String perm : SetPermissions.necromancienPermission) {
				permi.get(player.getUniqueId()).setPermission(perm, true);
			}
			return;

		case 6:
			for (String perm : SetPermissions.helpeurPermission) {
				permi.get(player.getUniqueId()).setPermission(perm, true);
			}
			return;

		case 7:
			for (String perm : SetPermissions.modoPermission) {
				permi.get(player.getUniqueId()).setPermission(perm, true);
			}
			return;

		case 8:
			for (String perm : SetPermissions.supermodoPermission) {
				permi.get(player.getUniqueId()).setPermission(perm, true);
			}
			return;

		case 9:
			for (String perm : SetPermissions.adminPermission) {
				permi.get(player.getUniqueId()).setPermission(perm, true);
			}
			return;

		default:
			return;
		}
	}
}
