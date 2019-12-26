package aergoJDBCTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AergoJDBCConnect {

  private static Connection conn;

  AergoJDBCConnect() {}

  public static Connection getConnect() {
    if (conn != null) {
      return conn;
    }

    String aergoClassDriverName = "org.aergojdbc.JDBC";
    String network_url = "jdbc:aergo:alpha1.aergo.io:7845";
    String contractAddress = "AmgPVZfrYiKP8k7cxU8HuSGoCWgjwhwYeuRGR8NGBewCBbH4CFVL";
    String encryptedPassword =
        "47L5pSrb4r9WYMtrQh3ReF9DLoe4pFmeHWQ1dSJPTijYt7QquydQfnPeNHyQ9nE5xm1DZG3NW";
    String password = "1234";

    StringBuilder sb = new StringBuilder();
    String aergojdbc_url = sb.append(network_url).append("@").append(contractAddress).toString();

    try {
      Class.forName(aergoClassDriverName);
      conn = DriverManager.getConnection(aergojdbc_url, encryptedPassword, password);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return conn;
  }
}
