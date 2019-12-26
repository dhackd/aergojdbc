package aergoJDBCTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main implements Runnable {

  static Connection conn = AergoJDBCConnect.getConnect();

  public static void main(String[] args) throws SQLException {

    // Main main = new Main();
    // Thread t1 = new Thread(main);
    // t1.start();

    // findTotalRownum();
    // findAll();
    findByDeviceId("1000");

  }

  private static void summarySupplier() throws SQLException {
    String sql = "select sum(supplier) from customer_gas_usage group by device_id";


  }

  private static void findTotalRownum() throws SQLException {
    int count = 0;
    String sql = "select count(*) from customer_gas_usage";
    Statement statement = conn.createStatement();
    ResultSet rs = statement.executeQuery(sql);
    while (rs.next()) {
      count = rs.getInt(1);
    }
    System.out.println("number of row = " + count);
  }

  private static void findByDeviceId(String deviceId) throws SQLException {
    String sql =
        "select device_id, supplier, rssi, battery, status, datetime, total_flow, prsessure from customer_gas_usage where device_id = ?";
    PreparedStatement pstmt;
    pstmt = conn.prepareStatement(sql);
    pstmt.setString(1, deviceId);
    ResultSet rs = pstmt.executeQuery();
    while (rs.next()) {
      System.out.println("device_id = " + rs.getString("device_id"));
      System.out.println("supplier = " + rs.getString("supplier"));
      System.out.println("rssi = " + rs.getString("rssi"));
      System.out.println("battery = " + rs.getString("battery"));
      System.out.println("status = " + rs.getString("status"));
      System.out.println("datetime = " + rs.getString("datetime"));
      System.out.println("total_flow = " + rs.getString("total_flow"));
      System.out.println("prsessure = " + rs.getString("prsessure"));
    }
  }

  private static void insert(String deviceId, String supplier, String rssi, String battery,
      String status, String datetime, String total_flow, String prsessure) {
    String sql =
        "insert into customer_gas_usage (device_id, supplier, rssi, battery, status, datetime, total_flow, prsessure) values( ? , ? , ? , ? , ? , ? , ? , ? )";
    PreparedStatement pstmt;
    try {
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, deviceId);
      pstmt.setString(2, supplier);
      pstmt.setString(3, rssi);
      pstmt.setString(4, battery);
      pstmt.setString(5, status);
      pstmt.setString(6, datetime);
      pstmt.setString(7, total_flow);
      pstmt.setString(8, prsessure);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private static void findAll() throws SQLException {
    Statement statement = conn.createStatement();
    statement.setFetchSize(100);
    ResultSet rs = statement.executeQuery(
        "select device_id, supplier, rssi, battery, status, datetime, total_flow, prsessure from customer_gas_usage");
    while (rs.next()) {
      System.out.println("device_id = " + rs.getString("device_id"));
      System.out.println("supplier = " + rs.getString("supplier"));
      System.out.println("rssi = " + rs.getString("rssi"));
      System.out.println("battery = " + rs.getString("battery"));
      System.out.println("status = " + rs.getString("status"));
      System.out.println("datetime = " + rs.getString("datetime"));
      System.out.println("total_flow = " + rs.getString("total_flow"));
      System.out.println("prsessure = " + rs.getString("prsessure"));
    }
  }

  @Override
  public void run() {
    int tmp = 0;
    for (int i = 0; i < 1000; i++) {
      try {
        tmp = i + 21;
        System.out.println("running.... tmp = " + tmp);
        Thread.sleep(1000);
        System.out.println("sleep... " + i + "sec");
        insert(Integer.toString(tmp), Integer.toString(tmp), Integer.toString(tmp),
            Integer.toString(tmp), Integer.toString(tmp), Integer.toString(tmp),
            Integer.toString(tmp), Integer.toString(tmp));
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
}
