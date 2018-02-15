package net.yuvideo.jgemstone.client.classes;


import java.nio.file.Paths;
import java.sql.*;
import java.util.logging.Logger;

/**
 * Created by zoom on 8/16/16.
 */
public class db_connection {
    public String query;
    public Settings local_settings;
    Logger LOGGER = Logger.getLogger("DB_CONNECTION");
    private Connection conn;
    private ResultSet rs;
    private Statement stmt;
    private String JDBC_DRIVER = "org.sqlite.JDBC";
    private String DB_URL = "jdbc:sqlite:.JGemstone.db";

    public void init_database() {
        System.out.println(Paths.get(".").toAbsolutePath().normalize().toString());
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        local_settings = new Settings();
        create_dataTable();
        get_settings();


    }


    private void create_dataTable() {
        query = "CREATE TABLE  IF NOT EXISTS Settings" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "REMOTE_HOST TEXT , " +
                "REMOTE_PORT INT);" +
                "INSERT OR IGNORE INTO Settings (ID) VALUES (1);";

        try {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void get_settings() {
        try {
            rs = stmt.executeQuery("SELECT * FROM Settings");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            local_settings.setREMOTE_HOST(rs.getString("REMOTE_HOST"));
            local_settings.setREMOTE_PORT(rs.getInt("REMOTE_PORT"));
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void set_settings() throws SQLException {
            query = String.format("UPDATE Settings SET " +
                            "REMOTE_HOST='%s', " +
                            "REMOTE_PORT='%d'",
                    local_settings.getREMOTE_HOST(),
                    local_settings.getREMOTE_PORT());

        LOGGER.info(query);
        stmt.executeUpdate(query);

    }

    public void close_db() {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
