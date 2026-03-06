package fr.midahe.sqlConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import fr.midahe.main.Main;

public class SqlConnection {

	private Connection connection;
	private String urlbase, host, database, user, password;

	public SqlConnection(String urlbase, String host, String database, String user, String password) {
		this.urlbase = urlbase;
		this.host = host;
		this.database = database;
		this.user = user;
		this.password = password;

	}

	public void connect() {

		if (connection != null)
			return;

		try {
			connection = DriverManager.getConnection(urlbase + host + "/" + database, user, password);
			Main.console.sendMessage("§d" + urlbase + host + "/" + database + " §aConnected");
		} catch (SQLException e) {
			Main.console.sendMessage("§4[Error]: §cConnection §d" + urlbase + host + "/" + database + ": §cCannot " + '\n' + "                          §aConnect §cto this device");
		}
	}

	public void disconnect() {

		if (connection == null)
			return;

		try {
			connection.close();
			Main.console.sendMessage("§d" + urlbase + host + "/" + database + " §cDisconnected");
		} catch (SQLException e) {
			Main.console.sendMessage("§4[Error]: §cConnection §d" + urlbase + host + "/" + database + ": §cCannot " + '\n' + "                          §4Disconnect §cto this device");
		}

	}

	public Connection getConnection() {
		return connection;
	}
}