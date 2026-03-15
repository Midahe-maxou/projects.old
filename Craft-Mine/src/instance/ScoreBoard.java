package instance;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import main.Variables;

public class ScoreBoard {
	
	public static ScoreboardManager manager;
	public static Scoreboard board;
	public static Objective obj = null;

	public ScoreBoard() {
		sideBoard();
	}

	public void sideBoard() {
		
		ScoreBoard.manager = Bukkit.getServer().getScoreboardManager();
		ScoreBoard.board = ScoreBoard.manager.getMainScoreboard();
		ScoreBoard.obj = ScoreBoard.board.registerNewObjective("sideBoard", "dummy", "§6Craft§3&§eMine");
		ScoreBoard.obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		int minutes = Time.getMinutes(), seconds = Time.getSeconds();
		String min, sec;

		if (minutes < 10)
			min = "0" + minutes;
		else
			min = "" + minutes;

		if (seconds < 10)
			sec = "0" + seconds;
		else
			sec = "" + seconds;

		Score score;
		score = obj.getScore("§7-------------");
		score.setScore(99);
		score = obj.getScore("§7Temps: " + min + ":" + sec);
		score.setScore(98);
		score = obj.getScore("§7-------------");
		score.setScore(97);

		for (Player p : Bukkit.getOnlinePlayers()) {
			String points = Variables.points.get(p.getUniqueId());
			score = ScoreBoard.obj.getScore("§5" + p.getName() + ": §d" + points + " points");
			score.setScore(Integer.valueOf(points));
			p.setScoreboard(ScoreBoard.board);
		}
	}

	// @SuppressWarnings("deprecation")
	private void nameBoard() {

		/*
		 * ScoreboardManager manager = Bukkit.getScoreboardManager(); Scoreboard board =
		 * manager.getNewScoreboard(); Objective obj =
		 * board.registerNewObjective("sideBoard", "dummy");
		 * obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
		 * 
		 * for(Player p : Bukkit.getOnlinePlayers()) {
		 * 
		 * String points = Variables.points.get(p.getUniqueId());
		 * 
		 * obj.setDisplayName("§6" + p.getName() + ": §e" + points + " points");
		 * p.setScoreboard(board); }
		 */
	}
}
