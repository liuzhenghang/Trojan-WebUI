package cn.qxhua21.trojan.Dao;

import cn.qxhua21.trojan.lei.User;
import cn.qxhua21.trojan.sha.md5;
import cn.qxhua21.trojan.sha.sha;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UrlDao {
    public static ArrayList<ArrayList> trojan_url(String user) throws SQLException {
        sha sha=new sha();
        md5 m=new md5();
        BaseDao db=new BaseDao();
        String sql="select passroot from users where username=?";
        ResultSet rs=db.query(sql,user);
        rs.last();
        String pwdRoot=rs.getString("passroot");
        rs.close();
        sql="select * from servers";
        rs=db.query(sql);
        ArrayList<String> type =new ArrayList<>();
        ArrayList<String> address =new ArrayList<>();
        ArrayList<String> url =new ArrayList<>();
        ArrayList<String> bandwidth =new ArrayList<>();
        while (rs.next()){
            bandwidth.add(rs.getString("bandwidth"));
            type.add(rs.getString("type"));
            address.add(rs.getString("address"));
            url.add(rs.getString("type")+"://"
                    +m.getMd5(pwdRoot)+"@"
                    +rs.getString("addr")+":"+rs.getInt("port"));
        }
        ArrayList<ArrayList> urls=new ArrayList<>();
        urls.add(type);
        urls.add(address);
        urls.add(url);
        urls.add(bandwidth);
        return urls;
    }
    public static String clashURL(String user,String url) throws SQLException {
        sha sha=new sha();
        md5 m=new md5();
        BaseDao db=new BaseDao();
        String sql="select passroot from users where username=?";
        ResultSet rs=db.query(sql,user);
        rs.last();
        String pwdRoot=rs.getString("passroot");
        rs.close();
        return (url+"?urltoken="+m.getMd5(pwdRoot)).replace("TrojanUrl","Clash");
    }

    public static boolean resUrl(User user) throws SQLException {
        sha sha=new sha();
        md5 m=new md5();
        String pwd=sha.encryptThisString(user.getPwd());
        String sql="update users set passroot=NOW() where username=? and upassword=?";
        BaseDao db=new BaseDao();
        int num=db.update(sql,user.getName(),pwd);
        if (num==0){
            return false;
        }else {
            sql="select passroot from users where username=?";
            ResultSet rs=db.query(sql,user.getName());
            rs.last();
            String pwdRoot=sha.encryptThisString(m.getMd5(rs.getString("passroot")));

            sql="update users set password=? where username=?";
            db.update(sql,pwdRoot,user.getName());
            return true;
        }
    }
}
