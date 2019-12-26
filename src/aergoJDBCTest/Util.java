package aergoJDBCTest;

import java.sql.ResultSet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Util {

  @SuppressWarnings("unchecked")
  public static JSONArray convertToJSON(ResultSet resultSet) throws Exception {
    JSONArray jsonArray = new JSONArray();
    while (resultSet.next()) {
      int total_rows = resultSet.getMetaData().getColumnCount();
      System.out.println("total_rows = " + total_rows);
      for (int i = 0; i < total_rows; i++) {
        JSONObject obj = new JSONObject();
        obj.put(resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase(),
            resultSet.getObject(i + 1));
        jsonArray.add(obj);
      }
    }
    return jsonArray;
  }

  public static Object getKey(JSONArray array, String key) {
    Object value = null;
    for (int i = 0; i < array.size(); i++) {
      JSONObject item = (JSONObject) array.get(i);
      if (item.keySet().contains(key)) {
        value = item.get(key);
        break;
      }
    }
    return value;
  }
}
