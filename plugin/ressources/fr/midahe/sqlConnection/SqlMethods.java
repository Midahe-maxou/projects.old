package fr.midahe.sqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

import fr.midahe.main.Main;

public class SqlMethods {

	SqlConnection sql = Main.sql;
	Connection co = sql.getConnection();
	ResultSet result;

	public void createAccount(Player player) {

		try {
			PreparedStatement q = co.prepareStatement("INSERT INTO player_ressources(uuid,grade,money) VALUES (?,?,?)");
			q.setString(1, player.getUniqueId().toString());
			q.setString(2, "membre");
			q.setInt(3, 100);
			q.execute();
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean hasAccount(Player player) {

		boolean hasAccount = false;

		PreparedStatement q;
		try {
			q = co.prepareStatement("SELECT uuid FROM player_ressources WHERE uuid = ?");
			q.setString(1, player.getUniqueId().toString());
			result = q.executeQuery();
			hasAccount = result.next();
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hasAccount;
	}

	public int getBalance(Player player) {

		int balance = 0;

		try {
			PreparedStatement q = co.prepareStatement("SELECT money FROM player_ressources WHERE uuid = ?");
			q.setString(1, player.getUniqueId().toString());
			result = q.executeQuery();
			while (result.next()) {
				balance = result.getInt("money");
			}
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return balance;
	}

	public void setBalance(Player player, int balance) {
		try {
			PreparedStatement q = co.prepareStatement("UPDATE player_ressources SET money = ? WHERE uuid = ?");
			q.setInt(1, balance);
			q.setString(2, player.getUniqueId().toString());
			q.execute();
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean addMoney(Player player, int amount) {

		if (amount < 1)
			return false;

		int bal = this.getBalance(player);

		this.setBalance(player, (bal + amount));

		return true;
	}

	public boolean removeMoney(Player player, int amount) {

		if (amount < 1)
			return false;

		int bal = this.getBalance(player);

		if (bal < amount)
			return false;

		this.setBalance(player, bal - amount);

		return true;
	}

	public String getGrade(Player player) {

		String grade = "membre";
		try {
			PreparedStatement q = co.prepareStatement("SELECT grade FROM player_ressources WHERE uuid = ?");
			q.setString(1, player.getUniqueId().toString());
			result = q.executeQuery();
			while (result.next()) {
				grade = result.getString("grade");
			}
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return grade;
	}

	public int getGradeValue(Player player) {

		
		//rankup : 2 -> 3 | 5 -> 6
		
		
		String s = getGrade(player);

		switch (s.toLowerCase()) {

		case "apprenti":
			return 1;

		case "sorcier":
			return 2;

		case "mage":
			return 3;

		case "devin":
			return 4;

		case "necromancien":
			return 5;

		case "helpeur":
			return 6;

		case "modo":
			return 7;

		case "supermodo":
			return 8;

		case "admin":
			return 9;

		case "owner":
			return 10;

		default:
			return 0;
		}

	}

	public void setgrade(Player player, String grade) {

		try {
			PreparedStatement q = co.prepareStatement("UPDATE player_ressources SET grade = ? WHERE uuid = ?");
			q.setString(1, grade.toLowerCase());
			q.setString(2, player.getUniqueId().toString());
			q.execute();
			q.close();
			player.sendMessage("§c§oveuiller déco-reco pour avoir votre nouveau grade");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setGrade(Player player, int gradeValue) {

		if (gradeValue < 0 || gradeValue > 9)
			return;
		
		switch (gradeValue) {
		case 1:
			setgrade(player, "apprenti");

		case 2:
			setgrade(player, "sorcier");
			;

		case 3:
			setgrade(player, "mage");

		case 4:
			setgrade(player, "devin");

		case 5:
			setgrade(player, "necromancien");

		case 6:
			setgrade(player, "helpeur");

		case 7:
			setgrade(player, "modo");

		case 8:
			setgrade(player, "supermodo");

		case 9:
			setgrade(player, "admin");

		default:
			return;

		}

	}
}