package cn.qxhua21.trojan.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddQuotaDao {
    public static String add(String cdk,String user) throws SQLException {

        String sql="select data from keyss where cdk=? and type=\"cdk\"";
        BaseDao db=new BaseDao();
        ResultSet rs=db.query(sql,cdk);
        rs.last();
        double quota=0;
        if (rs.getRow()==0){
            return "cdk_is_false";
        }else {
                quota=rs.getDouble("data");
        }
        rs.close();
        sql="select quota from users where username=?";
        rs=db.query(sql,user);
        rs.last();
        if (rs.getInt("quota")<0){
            return "full";
        }
        sql="update users set quota=(quota+?*1024*1024*1024) where username=?";
        int num=db.update(sql,quota,user);
        if (num==1){
            sql="delete from keyss where cdk=? and type=\"cdk\"";
            db.update(sql,cdk);
            return String.valueOf(quota);
        }else {
            return "false";
        }
    }
}
