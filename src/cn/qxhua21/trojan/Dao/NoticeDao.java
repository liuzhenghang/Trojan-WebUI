package cn.qxhua21.trojan.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NoticeDao {
    public static String Notice() throws SQLException {
        String sql="select notice from config";
        BaseDao db=new BaseDao();
        ResultSet rs=db.query(sql);
        rs.last();
        return rs.getString("notice");
    }
}
