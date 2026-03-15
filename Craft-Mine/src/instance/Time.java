package instance;

import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import commands.CommandGame;
import main.Config;

public class Time {

	public static boolean running = true;

	private static int minutes;
	private static int seconds;

	public static void stopit() {
		Time.running = false;
	}

	public Time() {

		Time.running = true;

		String[] temps = Config.time.split(":");

		try {
			Time.minutes = Integer.valueOf(temps[0]);
			Time.seconds = Integer.valueOf(temps[1]);
		} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
			e.printStackTrace();
			Bukkit.broadcastMessage("§4Une erreur a ete detectee dans le config.yml");
		}

		Timer chrono = new Timer();
		
		chrono.schedule(new TimerTask() {

			@Override
			public void run() {
				Bukkit.broadcastMessage("run began");
				new ScoreBoard();

				if (!running)
					chrono.cancel();

				seconds--;

				if (seconds == -1) {
					minutes--;
					seconds = 59;
				}

				if (minutes == 0 && seconds <= 10) {
					end(seconds);
				}
				Bukkit.broadcastMessage("run ended");
			}
		}, 0, 1000);
	}

	public static int getMinutes() {
		return minutes;
	}

	public static int getSeconds() {
		return seconds;
	}

	public static void setMinutes(int minutes) {
		Time.minutes = minutes;
	}

	public static void setSeconds(int seconds) {
		Time.seconds = seconds;
	}

	@SuppressWarnings("deprecation")
	private void end(int seconds) {
		if (seconds <= 0) {
			Time.stopit();
			CommandGame.endGame();
			return;
		}

		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendTitle("§6Craft§3&§eMine", "§cFin dans §6" + seconds + " secondes");
		}
	}
}
