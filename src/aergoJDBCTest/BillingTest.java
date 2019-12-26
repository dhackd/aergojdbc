package aergoJDBCTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BillingTest {

  static Connection conn = AergoJDBCConnect.getConnect();

  public static void main(String[] args) {
    
    //a사용자가 b사용자에게 구독요청
    //b사용자가 구독 승인
    //a사용자의 지갑에서 b사용자의 지갑으로 100aergo만큼 전송 (tx발생)
    //history table에 기록
    

  }
  
  private static void insert_cust_subscribe_hist_tbl(String to_user, String from_user, String email, int amount, String device_type, String device_id, String tx_id, String status, String datetime) {
    String sql =
        "insert into cust_subscribe_hist_tbl (to_user, from_user, email, amount, device_type, device_id, tx_id, status, datetime) values( ? , ? , ? , ?, ?, ?, ?, ?, ? )";
    PreparedStatement pstmt;
    try {
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, to_user);
      pstmt.setString(2, from_user);
      pstmt.setString(3, email);
      pstmt.setInt(4, amount);
      pstmt.setString(5, device_type);
      pstmt.setString(6, device_id);
      pstmt.setString(7, tx_id);
      pstmt.setString(8, status);
      pstmt.setString(9, datetime);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  private static void insert_cust_info_tbl(String user, String addr, String device_type, String device_id, String tx_id, String datetime) {
    String sql =
        "insert into cust_info_tbl (user, address, device_type, device_id, tx_id, datetime) values( ? , ? , ? , ?, ?, ? )";
    PreparedStatement pstmt;
    try {
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, user);
      pstmt.setString(2, addr);
      pstmt.setString(3, device_type);
      pstmt.setString(4, device_id);
      pstmt.setString(5, tx_id);
      pstmt.setString(6, datetime);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }    

  private static void insert_cust_bills_hist_tbl(String to_user, String from_user, int amount,
      String datetime) {
    String sql =
        "insert into cust_bills_hist_tbl (to_user, from_user, amount, datetime) values( ? , ? , ? , ? )";
    PreparedStatement pstmt;
    try {
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, to_user);
      pstmt.setString(2, from_user);
      pstmt.setInt(3, amount);
      pstmt.setString(4, datetime);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
