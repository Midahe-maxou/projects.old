package fr.midahe.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import fr.midahe.methods.AddPotionToEntity;

public class CommandMob implements CommandExecutor {

	int duration = 0;
	int amplifier = 0;
	boolean hideparticles = false;
	int i = 0;
	public static boolean isExistEffect = true;
	Player tp;
	boolean isOnPlayer = false;

	int X;
	int Y;
	int Z;

	Location loc;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String aliase, String[] args) {

		i = 0;
		isOnPlayer = false;
		isExistEffect = true;
		hideparticles = false;

		Player player = (Player) sender;

		if (args.length == 0) {
			sender.sendMessage("§cUsage: /" + aliase + " <action> [option]");
			return true;
		}

		if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
			sender.sendMessage("§a[Help]: §c/" + aliase + '\n' + "§6  Usage: §r/" + aliase + " <action> [options...]"
					+ '\n' + "§6    action: §r [kill, effect, tp]");
			return true;
		}

		if (args.length > 0) {
			for (Entity entity : Bukkit.getWorld("world").getEntities()) {
				if (entity instanceof Player || entity instanceof Item || entity.getType() == EntityType.VILLAGER
						|| entity instanceof LivingEntity == false) {
					continue;
				}

				if (args[0].equalsIgnoreCase("kill")) {
					entity.remove();
					i++;
				} else if (args[0].equalsIgnoreCase("effect")) {
					isExistEffect = true;
					if (args.length > 1) {

						if (args.length > 3 && args.length < 6) {

							try {

								duration = Integer.valueOf(args[2]);
								amplifier = (Integer.valueOf(args[3]) - 1);

								if (args.length == 5)
									hideparticles = Boolean.valueOf(args[4]);

							} catch (NumberFormatException ex) {
								sender.sendMessage(
										"§4[Erreur]: §cPour <Duration> et <Amplifier>, seul les nombres sont acceptés");
								return true;
							}

							if (args.length == 4) {
								new AddPotionToEntity(entity, args[2], duration, amplifier);
								if (isExistEffect) {
									sender.sendMessage("§eVous venez de mettre l'effet §5" + args[2] + " §eà un §b"
											+ entity.getType().toString().toLowerCase());
									continue;
								} else {
									sender.sendMessage("§4[Erreur]: §ccet effet n'existe pas");
									return true;
								}
							} else if (args.length == 5) {
								new AddPotionToEntity(entity, args[2], duration, amplifier, hideparticles);
								if (isExistEffect) {
									sender.sendMessage("§eVous venez de mettre l'effet §5" + args[2] + " §eà §b"
											+ entity.getType().toString().toLowerCase());
									continue;
								} else {
									sender.sendMessage("§4[Erreur]: §ccet effet n'existe pas");
									return true;
								}
							}

						} else {
							sender.sendMessage("§6Usage: §r/" + aliase + " " + args[0]
									+ " <effect> <duration> <amplifier> [hide particles]" + '\n' + "  §6Options:" + '\n'
									+ "    §6Duration: §rnumber" + '\n' + "    §6Amplifier: §rnumber(max: 255)" + '\n'
									+ "    §6Hide Particles: §rtrue or false");
							return true;
						}

					} else {

						sender.sendMessage("§6option: §r" + '\n' + "  [Absorption, Blindness, Fire_resistance," + '\n'
								+ "  Haste, Health_boost, Hunger, Invisibility," + '\n'
								+ "  Jump_boost, Mining_fatigue, Nausea, Night_vision," + '\n'
								+ "  Slowness, Speed, Strengh, Water_breathing," + '\n' + "  Weakness, Wither]");
						return true;
					}
				} else if (args[0].equalsIgnoreCase("tp")) {

					if (args.length == 1) {
						sender.sendMessage("§6Usage: §r/" + aliase + args[0] + " <player> or <X> <Y> <Z>");
						return true;
					}
					if (args.length == 2) {

						tp = Bukkit.getPlayer(args[1]);
						if (tp != null && tp instanceof Player) {

							entity.teleport(tp);
							i++;
							isOnPlayer = true;
							continue;
						} else {
							sender.sendMessage("§4[Erreur]: §cJoueur introuvable");
							return true;
						}

					} else if (args.length == 4) {
						try {

							if (args[1].equals("~")) {
								X = player.getLocation().getBlockX();
							} else {
								X = Integer.valueOf(args[1]);
							}

							if (args[2].equals("~")) {
								Y = player.getLocation().getBlockY();
							} else {
								Y = Integer.valueOf(args[2]);
							}

							if (args[3].equals("~")) {
								Z = player.getLocation().getBlockZ();
							} else {
								Z = Integer.valueOf(args[3]);
							}

							loc = new Location(Bukkit.getWorld("world"), X, Y, Z);

							entity.teleport(loc);
							i++;

							continue;
						} catch (NumberFormatException ex) {

							if (args[1].equals("~")) {
								X = player.getLocation().getBlockX();
							}else
							if (args[2].equals("~")) {
								Y = player.getLocation().getBlockY();
							}else
							if (args[3].equals("~")) {
								Z = player.getLocation().getBlockZ();
							}else {
								sender.sendMessage("§4[Erreur]: §c les valeurs X, Y et Z doivent §tre en chiffre");
								return true;
							}

						}
					} else {
						sender.sendMessage("§6Usage: §r/" + aliase + " " + args[0] + " <player> or <X> <Y> <Z>");
						return true;
					}
				}
			}

			if (args[0].equalsIgnoreCase("kill")) {
				sender.sendMessage("§cVous avez retiré §4" + i + " §centités");
			} else if (args[0].equalsIgnoreCase("tp") && isOnPlayer) {
				sender.sendMessage("§eVous avez téléporté §b" + i + " créatures §esur §5" + tp.getName());
				tp.sendMessage("§eOn vous a offert un petit cadeau =)");
			}
			if (args[0].equalsIgnoreCase("tp") && !isOnPlayer) {
				sender.sendMessage("§eVous avez téléporté §b" + i + " créatures §een §dX=" + X + " Y=" + Y + " Z=" + Z);
			}
		}
		return true;
	}

}
