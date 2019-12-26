package aergoJDBCTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.simple.JSONArray;

public class Main {

  static Connection conn = AergoJDBCConnect.getConnect();

  public static void main(String[] args) throws Exception {

    // insert("111-111-111", "korea power", "10", "90", "0", "20191226", "50", "1");
    // findTotalRownum();
    // findAll();
    findByDeviceId("111-111-111");

  }

  @SuppressWarnings("unused")
  private static void summarySupplier() throws SQLException {
    String sql = "select sum(supplier) from customer_gas_usage_tbl group by device_id";
  }

  @SuppressWarnings("unused")
  private static void findTotalRownum() throws SQLException {
    int count = 0;
    String sql = "select count(*) from cust_gas_usage";
    Statement statement = conn.createStatement();
    ResultSet rs = statement.executeQuery(sql);
    while (rs.next()) {
      count = rs.getInt(1);
    }
    System.out.println("number of row = " + count);
  }

  private static void findByDeviceId(String deviceId) throws Exception {
    String sql =
        "select device_id, supplier, rssi, battery, status, datetime, total_flow, prsessure from cust_gas_usage_tbl where device_id = ?";
    PreparedStatement pstmt;
    JSONArray ja = new JSONArray();
    pstmt = conn.prepareStatement(sql);
    pstmt.setString(1, deviceId);
    ResultSet rs = pstmt.executeQuery();

    /*
     * ResultSet to JSONArray
     */
    ja = Util.convertToJSON(rs);
    System.out.println(ja.toJSONString());

    /*
     * key값 기준으로 value 리턴 ex : key("total_flow")
     */
    Object obj = new Object();
    obj = Util.getKey(ja, "total_flow");
    System.out.println("key is value = " + obj);
  }

  @SuppressWarnings("unused")
  private static void insert(String deviceId, String supplier, String rssi, String battery,
      String status, String datetime, String total_flow, String prsessure) {
    String sql =
        "insert into cust_gas_usage_tbl (device_id, supplier, rssi, battery, status, datetime, total_flow, prsessure) values( ? , ? , ? , ? , ? , ? , ? , ? )";
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

  @SuppressWarnings("unused")
  private static void findAll() throws SQLException {
    Statement statement = conn.createStatement();
    statement.setFetchSize(100);
    ResultSet rs = statement.executeQuery(
        "select device_id, supplier, rssi, battery, status, datetime, total_flow, prsessure from cust_gas_usage_tbl");
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
}
