package toConnectDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHSQL {
	private static final String DRIVER_CLASS = "org.hsqldb.jdbcDriver";
	private static Connection cnx = null;
	private static String usuario = "SA";
	private static String senha = "";
	private static String PathBase = "C:\\Users\\jjorg\\eclipse-workspace\\projetoPOO2\\database\\loja";
	private static final String URL = "jdbc:hsqldb:file:" + PathBase + ";shutdown=true;hsqldb.write_delay=false; ";

	public static Connection conectar() {
		if (cnx == null) {
			try {
				Class.forName(DRIVER_CLASS);
				cnx = DriverManager.getConnection(URL, usuario, senha);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return cnx;
	}

	public static void fecharCNX() {
		try {
			cnx.close();
			cnx = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
