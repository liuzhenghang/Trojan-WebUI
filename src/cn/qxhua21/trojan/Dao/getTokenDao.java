package cn.qxhua21.trojan.Dao;

import cn.qxhua21.trojan.sha.sha;

import java.text.SimpleDateFormat;
import java.util.Date;

public class getTokenDao {
    public static String getToken(String user){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String token=df.format(new Date());
        sha sha=new sha();
        token=sha.encryptThisString(token);
        String sql="update tokens set time=NOW(),token=? where username=?";
        BaseDao db=new BaseDao();
        db.update(sql,token,user);
        return token;
    }
}
