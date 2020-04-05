package cn.qxhua21.trojan.Dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class dataUseDao {

    public static double used(String name) throws SQLException {
        String sql="select upload/1024/1024 + download/1024/1024 AS used from users WHERE username=?";
        BaseDao db=new BaseDao();
        ResultSet rs=db.query(sql,name);
        double used=0;
        while (rs.next()){
            used=rs.getDouble("used")/1024;
            BigDecimal bg = new BigDecimal(used);
            used = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        return used;
    }

    public static ArrayList<ArrayList<String>> allUsed(String user) throws SQLException {

        ArrayList<ArrayList<String>> date=new ArrayList<>();
        ArrayList<String> used=new ArrayList<>();
        ArrayList<String> time=new ArrayList<>();
        BaseDao db=new BaseDao();
        String sql="select used/1024/1024/1024 as used,date FROM used where username=? ORDER BY date DESC";
        ResultSet rs=db.query(sql,user);
        while (rs.next()){
            double ed=rs.getDouble("used");
            BigDecimal bg = new BigDecimal(ed);
            ed= bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            used.add(String.valueOf(ed));
            time.add(rs.getString("date"));
        }
        date.add(used);
        date.add(time);
        return date;
    }

    public static double quota(String user) throws SQLException {
        String sql="select quota/1024/1024/1024 as quo from users where username=?";
        BaseDao db=new BaseDao();
        ResultSet rs=db.query(sql,user);
        double quota=0;
        while (rs.next()){
            quota=rs.getDouble("quo");
        }
        if (quota<=0){
            quota=2048;
        }
        BigDecimal bg = new BigDecimal(quota);
        quota= bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return quota;
     }
}
