package fr.midahe.time;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

public class Time {

	public volatile static boolean running = true;

	private static int hours = 0;
	private static int minutes = 0;
	private static int seconds = 0;

	ArrayList<Player> arr = new ArrayList<>();
	ArrayList<Entity> array = new ArrayList<>();

	public static void stopit() {
		Time.running = false;
	}

	public Time() {

		Time.running = true;
		hours = 0;
		minutes = 0;
		seconds = 0;

		Timer chrono = new Timer();

		chrono.schedule(new TimerTask() {

			@Override
			public void run() {

				if (!running) {
					chrono.cancel();
				}

				seconds++;

				if (seconds == 60) {
					minutes++;
					seconds = 0;
				}

				if (minutes == 60) {
					hours++;
					minutes = 0;
				}

				if (hours == 24) {
					seconds = 0;
					minutes = 0;
					hours = 0;
				}

				int remainsSeconds = 60 - seconds;

				if (minutes == 5 || minutes == 15 || minutes == 25 || minutes == 35 || minutes == 45 || minutes == 55) {

					if (seconds == 0) {
						Bukkit.broadcastMessage("§4[ClearLag]: §6Clear des items au sol dans §c5 §6minutes");
					}
				}

				if (minutes == 8 || minutes == 18 || minutes == 28 || minutes == 38 || minutes == 48 || minutes == 58) {
					if (seconds == 30) {
						Bukkit.broadcastMessage("§4[ClearLag]: §6Clear des items au sol dans §c2 §6minutes");
					}
				}

				if (minutes == 9 || minutes == 19 || minutes == 29 || minutes == 39 || minutes == 49 || minutes == 59) {
					if (seconds == 0) {
						Bukkit.broadcastMessage("§4[ClearLag]: §6Clear des items au sol dans §c1 §6minutes");
					}

					if (seconds == 30) {
						Bukkit.broadcastMessage("§4[ClearLag]: §6Clear des items au sol dans §c30 §6secondes");
					}

					if (seconds >= 50 && seconds != 0) {
						Bukkit.broadcastMessage(
								"§4[ClearLag]: §6Clear des items au sol dans §c" + remainsSeconds + " §6secondes");

					}
				}
				if (minutes == 0 || minutes == 10 || minutes == 20 || minutes == 30 || minutes == 40 || minutes == 50) {
					if (seconds == 0) {

						array = (ArrayList<Entity>) Bukkit.getWorld("world").getEntities();
						int number = 0;

						for (Entity entity : array) {
							if (entity instanceof Item) {
								entity.remove();
								number++;
							}
						}

						if (number == 0) {
							Bukkit.broadcastMessage("§4[ClearLag]: §6Aucun item n'a été enlevé");
						} else if (number == 1) {
							Bukkit.broadcastMessage("§4[ClearLag]: §c1 §6item a été enlevé");
						} else {
							Bukkit.broadcastMessage("§4[ClearLag]: §c" + number + " §6items ont été enlevé");
						}
					}
				}

			}
		}, 1000, 1000);
	}

	public static int getHours() {
		return hours;
	}

	public static int getMinutes() {
		return minutes;
	}

	public static int getSeconds() {
		return seconds;
	}

	public static void setHours(int hours) {
		Time.hours = hours;
	}

	public static void setMinutes(int minutes) {
		Time.minutes = minutes;
	}

	public static void setSeconds(int seconds) {
		Time.seconds = seconds;
	}
}
