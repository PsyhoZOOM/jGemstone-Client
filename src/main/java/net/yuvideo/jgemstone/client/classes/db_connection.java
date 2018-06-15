package net.yuvideo.jgemstone.client.classes;


import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 * Created by zoom on 8/16/16.
 */
public class db_connection {

  public String query;
  private Settings local_settings;
  Logger LOGGER = Logger.getLogger("DB_CONNECTION");
  private Connection conn;
  private ResultSet rs;
  private Statement stmt;
  private String JDBC_DRIVER = "org.sqlite.JDBC";
  private String DB_URL = "jdbc:sqlite:.JGemstone.db";

  public db_connection() {
    get_settings();
  }

  public void init_database() {
    try {
      Class.forName(JDBC_DRIVER);
      conn = DriverManager.getConnection(DB_URL);
      stmt = conn.createStatement();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    create_dataTable();


  }


  private void create_dataTable() {
    query = "CREATE TABLE  IF NOT EXISTS Settings" +
        "(ID INTEGER PRIMARY KEY AUTOINCREMENT , " +
        "REMOTE_HOST TEXT , " +
        "REMOTE_PORT INT , "
        + "USERNAME TEXT, "
        + "PASSWORD TEXT);" +
        "INSERT OR IGNORE INTO Settings (ID) VALUES (1);";

    try {
      stmt.executeUpdate(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }


  }

  public void get_settings() {
    init_database();
    local_settings = new Settings();
    try {
      rs = stmt.executeQuery("SELECT * FROM Settings");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try {
      local_settings.setREMOTE_HOST(rs.getString("REMOTE_HOST"));
      local_settings.setREMOTE_PORT(rs.getInt("REMOTE_PORT"));
      local_settings.setLocalUser(rs.getString("USERNAME"));
      local_settings.setLocalPassword(rs.getString("PASSWORD"));
    } catch (SQLException e) {
      e.printStackTrace();
    }

    close_db();
  }

  public void set_settings() {
    init_database();
    md5_digiest md5_digiest = new md5_digiest(local_settings.getLocalPassword());
    query = String.format("UPDATE Settings SET " +
            "REMOTE_HOST='%s', " +
            "REMOTE_PORT='%d', USERNAME='%s' , PASSWORD='%s'",
        local_settings.getREMOTE_HOST(),
        local_settings.getREMOTE_PORT(),
        local_settings.getLocalUser(),
        md5_digiest.get_hash());

    LOGGER.info(query);
    try {
      stmt.executeUpdate(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    close_db();
  }

  public Settings getLocal_settings() {
    return local_settings;
  }

  public void setLocal_settings(Settings local_settings) {
    this.local_settings = local_settings;
  }

  public void close_db() {
    try {
      if (rs != null) {
        rs.close();
      }
      if (stmt != null) {
        stmt.close();
      }
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }


  }
}
