package cn.qxhua21.trojan.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TokenDao {
    public static boolean token(String token,String name) throws SQLException {
        String sql="select timediff(NOW(),time)<3600 as timeover from tokens where token=? and username=?";
        BaseDao db=new BaseDao();
        ResultSet rs=db.query(sql,token,name);
        rs.last();
        if(rs.getRow()>0){
            rs.first();
            int f=rs.getInt("timeover");
            if (f==1){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    public static boolean letToken(String token,String name) throws SQLException {
        String sql="select timediff(NOW(),time)<300 as timeover from tokens where token=? and username=?";
        BaseDao db=new BaseDao();
        ResultSet rs=db.query(sql,token,name);
        rs.last();
        if(rs.getRow()>0){
            rs.first();
            int f=rs.getInt("timeover");
            if (f==1){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }
}
