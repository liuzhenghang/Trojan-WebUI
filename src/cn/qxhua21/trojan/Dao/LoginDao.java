package cn.qxhua21.trojan.Dao;

import cn.qxhua21.trojan.lei.User;
import cn.qxhua21.trojan.sha.sha;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {
    public static boolean login(User user) throws SQLException {
        BaseDao bd=new BaseDao();
        String sql="select * from users where username=? and upassword=?";
        sha sha=new sha();
        String pwdsha=sha.encryptThisString(user.getPwd());
        ResultSet rs=bd.query(sql,user.getName(),pwdsha);
        rs.last();
        if (rs.getRow()>0){
            return true;
        }else {
            return false;
        }
    }

    public static boolean haveQuestion(User user) throws SQLException {
        BaseDao bd=new BaseDao();
        String sql="select * from questions where username=?";
        ResultSet rs=bd.query(sql,user.getName());
        rs.last();
        if (rs.getRow()>0){
            return true;
        }else {
            return false;
        }
    }
}
