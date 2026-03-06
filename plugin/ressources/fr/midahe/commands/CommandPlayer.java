package fr.midahe.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import fr.midahe.methods.AddPotionToEntity;

public class CommandPlayer implements CommandExecutor {

	int duration = 0;
	int amplifier = 0;
	boolean hideparticles = false;
	public static boolean isExistEffect = true;

	int X = 0;
	int Y = 0;
	int Z = 0;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String aliase, String[] args) {
		
		if(sender instanceof Player && !sender.hasPermission("command.player")) {
			sender.sendMessage("§4[Erreur]: §cVous n'avez pas la permission");
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage("§cUsage: /" + aliase + " <target> <action> [option]");
			return true;
		}

		if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
			sender.sendMessage("§a[Help]: §/" + aliase + '\n' + "§6  Usage: §r/" + aliase
					+ " <target> <action> [options...]" + '\n' + "§6    target: §r@ for all target" + '\n'
					+ "§6    action: §r [kill, effect, tp, clear]");
			return true;
		}

		if (args.length > 1) {

			if (Bukkit.getPlayer(args[0]) != null) {

				Player target = Bukkit.getPlayer(args[0]);

				if (args[1].equalsIgnoreCase("kill")) {
					target.setHealth(0);
					sender.sendMessage("§cVous venez de tuer §b" + target.getName());
					target.sendMessage("§eVous venez de vous faire anéantir");
					return true;
				} else if (args[1].equalsIgnoreCase("effect")) {

					isExistEffect = true;

					if (args.length > 2) {

						if (args[2].equalsIgnoreCase("clear")) {
							for (PotionEffect po : target.getActivePotionEffects()) {
								target.removePotionEffect(po.getType());
							}
							if (target.getName() == sender.getName()) {
								sender.sendMessage("§6Vous venez de retirer §ctout §6vos effets");
								return true;
							} else {
								sender.sendMessage("§6Vous venez de retirer §cTout les effets §6de §b" + target.getName());
								target.sendMessage("§6On vient de vous retirer §cTout §6vos effets");
								return true;
							}
						}

						if (args.length > 4) {

							try {

								duration = Integer.valueOf(args[3]);
								amplifier = (Integer.valueOf(args[4]) - 1);

								if (args.length == 6)
									hideparticles = Boolean.valueOf(args[5]);

							} catch (NumberFormatException ex) {
								sender.sendMessage(
										"§4[Erreur]: §cPour <Duration> et <Amplifier>, seul les nombres sont acceptés");
								return true;
							}

							if (args.length == 5) {
								new AddPotionToEntity(target, args[2], duration, amplifier);
								if (isExistEffect) {
									sender.sendMessage("§eVous venez de mettre l'effet §5" + args[2] + " §eà §b"
											+ target.getName());
									target.sendMessage("§eVous venez de reçevoir l'effet §5" + args[2] + " §eniveau §3"
											+ (amplifier + 1) + " §ependant §b" + duration + " secondes");
								} else {
									sender.sendMessage("§4[Erreur]: §ccet effet n'existe pas");
								}
								return true;
							} else if (args.length == 6) {
								new AddPotionToEntity(target, args[2], duration, amplifier, hideparticles);
								if (isExistEffect) {
									sender.sendMessage("§eVous venez de mettre l'effet §5" + args[2] + " §eà §b"
											+ target.getName());
									target.sendMessage("§eVous venez de reçevoir l'effet §5" + args[2] + " §eniveau §3"
											+ (amplifier + 1) + " §ependant §b" + duration + " secondes" + '\n'
											+ "§6Mais comme vous avez beaucoup de chance, on vous a §cdésactivé §6les particules");
								} else {
									sender.sendMessage("§4[Erreur]: §ccet effet n'existe pas");
								}
								return true;
							}

						} else {
							sender.sendMessage("§6Usage: §r/" + aliase + " " + target.getName() + " " + args[1]
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
				} else if (args[1].equalsIgnoreCase("tp")) {

					if (args.length == 2) {
						sender.sendMessage("§6Usage: §r/" + aliase + " " + target.getName() + " " + args[1]
								+ " <target> or <X> <Y> <Z>");
						return true;
					}

					if (args.length == 3) {

						Player tp = Bukkit.getPlayer(args[2]);
						if (tp != null && tp instanceof Player) {

							target.teleport(tp);

							sender.sendMessage(
									"§eVous avez t§l§port§ §b" + target.getName() + " §evers §b" + tp.getName());
							target.sendMessage("§eVous avez été téléporté vers §b" + tp.getName());
							tp.sendMessage("§b" + target.getName() + " §es'est fait téléporté vers vous");

						} else {
							sender.sendMessage("§4[Erreur]: §cJoueur introuvable");
							return true;
						}

					} else if (args.length == 5) {
						try {
							X = Integer.valueOf(args[2]);
							Y = Integer.valueOf(args[3]);
							Z = Integer.valueOf(args[4]);

							Location loc = new Location(Bukkit.getWorld("world"), X, Y, Z);

							target.teleport(loc);

							sender.sendMessage("§eVous avez téléporté §b" + target.getName() + " §een §dX=" + X + " Y="
									+ Y + " Z=" + Z);
							target.sendMessage("§eVous venez d'être téléporté en §dX=" + X + " Y=" + Y + " Z=" + Z);
							return true;
						} catch (NumberFormatException ex) {
							sender.sendMessage("§4[Erreur]: §c les valeurs X, Y et Z doivent être en chiffre");
							return true;
						}
					} else {
						sender.sendMessage("§6Usage: §r/" + aliase + " " + target.getName() + " " + args[1]
								+ " <target> or <X> <Y> <Z>");
						return true;
					}
				} else if (args[1].equalsIgnoreCase("clear")) {
					if (args.length == 2) {
						target.getInventory().clear();
						sender.sendMessage("§eVous venez de clear l'inventaire de §b" + target.getName());
						target.sendMessage("§eVous venez de vous faire clear votre inventaire");
					}
				} else {
					sender.sendMessage("§4[Erreur]: §ccette action n'existe pas !");
				}

			} else if (args[0].equals("@")) {

				for (Player target : Bukkit.getWorld("world").getPlayers()) {

					if (args[1].equalsIgnoreCase("kill")) {

						if (args[1].equals(sender.getName())) {
							continue;
						}

						if (args.length == 2) {
							
							Bukkit.getWorld("world").setGameRuleValue("keepInventory", "true");

							int xp = target.getLevel();
							target.setHealth(0);
							target.setLevel(xp);
							sender.sendMessage("§cVous venez de tuer §b" + target.getName());
							target.sendMessage("§eVous venez de vous faire an§antir");

							Bukkit.getWorld("world").setGameRuleValue("keepInventory", "false");
							continue;
						} else {
							sender.sendMessage("§6Usage: §r /" + aliase + " @ kill");
							return true;
						}
					} else if (args[1].equalsIgnoreCase("effect")) {

						if (args[1].equals(sender.getName())) {
							continue;
						}

						if (args.length > 2) {

							if (args[2].equalsIgnoreCase("clear")) {
								for (PotionEffect po : target.getActivePotionEffects()) {
									target.removePotionEffect(po.getType());
								}
								sender.sendMessage(
										"§6Vous venez de retirer §cTout les effets §6de §b" + target.getName());
								target.sendMessage("§6On vient de vous retirer §ctout §6vos effets");
								return true;
							}
							if (args.length > 4) {

								try {

									duration = Integer.valueOf(args[3]);
									amplifier = (Integer.valueOf(args[4]) - 1);

									if (args.length == 6)
										hideparticles = Boolean.valueOf(args[5]);

								} catch (NumberFormatException ex) {
									sender.sendMessage(
											"§4[Erreur]: §cPour <Duration> et <Amplifier>, seul les nombres sont acceptés");
									return true;
								}

								if (args.length == 5) {
									new AddPotionToEntity(target, args[2], duration, amplifier);
									if (isExistEffect) {
										sender.sendMessage("§eVous venez de mettre l'effet §5" + args[2] + " §eà §b"
												+ target.getName());
										target.sendMessage(
												"§eVous venez de re§evoir l'effet §5" + args[2] + " §eniveau §3"
														+ (amplifier + 1) + " §ependant §b" + duration + " secondes");
									} else {
										sender.sendMessage("§4[Erreur]: §ccet effet n'existe pas");
										return true;
									}
									continue;
								} else if (args.length == 6) {
									new AddPotionToEntity(target, args[2], duration, amplifier, hideparticles);
									if (isExistEffect) {
										sender.sendMessage("§eVous venez de mettre l'effet §5" + args[2] + " §eà §b"
												+ target.getName());
										target.sendMessage("§eVous venez de re§evoir l'effet §5" + args[2]
												+ " §eniveau §3" + (amplifier + 1) + " §ependant §b" + duration
												+ " secondes" + '\n'
												+ "§6Mais comme vous avez beaucoup de chance, on vous a §cdésactivé §6les particules");
									} else {
										sender.sendMessage("§4[Erreur]: §ccet effet n'existe pas");
										return true;
									}
									continue;
								} else {
									sender.sendMessage("§6Usage: §r/" + aliase + " " + target.getName() + " " + args[1]
											+ " <effect> <duration> <amplifier> [hide particles]" + '\n'
											+ "  §6Options:" + '\n' + "    §6Duration: §rnumber" + '\n'
											+ "    §6Amplifier: §rnumber(max: 255)" + '\n'
											+ "    §6Hide Particles: §rtrue or false");
									return true;
								}

							} else {
								sender.sendMessage("§6Usage: §r/" + aliase + " " + target.getName() + " " + args[1]
										+ " <effect> <duration> <amplifier> [hide particles]" + '\n' + "  §6Options:"
										+ '\n' + "    §6Duration: §rnumber" + '\n'
										+ "    §6Amplifier: §rnumber(max: 255)" + '\n'
										+ "    §6Hide Particles: §rtrue or false");
								return true;
							}

						} else {

							sender.sendMessage("§6option: §r" + '\n' + "  [Absorption, Blindness, Fire_resistance,"
									+ '\n' + "  Haste, Health_boost, Hunger, Invisibility," + '\n'
									+ "  Jump_boost, Mining_fatigue, Nausea, Night_vision," + '\n'
									+ "  Slowness, Speed, Strengh, Water_breathing," + '\n' + "  Weakness, Wither]");
							return true;
						}
					} else if (args[1].equalsIgnoreCase("tp")) {

						if (args.length == 2) {
							sender.sendMessage("§6Usage: §r/" + aliase + " " + target.getName() + " " + args[1]
									+ " <target> or <X> <Y> <Z>");
							return true;
						}

						if (args.length == 3) {

							Player tp = Bukkit.getPlayer(args[2]);
							if (tp != null && tp instanceof Player) {

								if (tp.getName().equals(sender.getName())) {
									sender.sendMessage(
											"§eVous avez téléporté §btout les joueurs §evers §b" + tp.getName());
									target.teleport(tp);
									continue;
								}

								target.teleport(tp);

								target.sendMessage("§eVous avez été téléporté vers §b" + tp.getName());
								tp.sendMessage("§b" + target.getName() + " §es'est fait téléporté vers vous");
								continue;

							} else {
								sender.sendMessage("§4[Erreur]: §cJoueur introuvable");
								return true;
							}

						} else if (args.length == 5) {
							try {
								int X = Integer.valueOf(args[2]);
								int Y = Integer.valueOf(args[3]);
								int Z = Integer.valueOf(args[4]);

								Location loc = new Location(Bukkit.getWorld("world"), X, Y, Z);

								target.teleport(loc);

								sender.sendMessage("§eVous avez téléporté §b" + target.getName() + " §een §dX=" + X
										+ " Y=" + Y + " Z=" + Z);
								target.sendMessage("§eVous venez d'§tre téléporté en §dX=" + X + " Y=" + Y + " Z=" + Z);
								continue;
							} catch (NumberFormatException ex) {
								sender.sendMessage("§4[Erreur]: §c les valeurs X, Y et Z doivent §tre en chiffre");
								return true;
							}
						} else {
							sender.sendMessage("§6Usage: §r/" + aliase + " " + target.getName() + " " + args[1]
									+ " <target> or <X> <Y> <Z>");
							return true;
						}
					} else if (args[1].equalsIgnoreCase("clear")) {
						if (target.getName().equalsIgnoreCase(sender.getName())) {
							sender.sendMessage("§4Tout les inventaires ont été clear par §c" + sender.getName());
							continue;
						}

						target.sendMessage("§o§ddit WTF dans le chat =) !");

					} else {
						sender.sendMessage("§4[Erreur]: §ccette action n'existe pas !");
					}
				}
				return true;
			} else {
				sender.sendMessage("§4[Erreur]: §cLe joueur " + args[0] + " n'est pas connecté");
			}
		} else {
			sender.sendMessage("§cUsage: /" + aliase + " <target> <action> [option]");
		}
		return true;
	}
}
