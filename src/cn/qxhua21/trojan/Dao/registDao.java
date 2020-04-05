package cn.qxhua21.trojan.Dao;

import cn.qxhua21.trojan.lei.User;
import cn.qxhua21.trojan.sha.md5;
import cn.qxhua21.trojan.sha.sha;

import java.sql.ResultSet;
import java.sql.SQLException;

public class registDao{
    public static String regist(User user,String comp,String ques,String ans) throws SQLException {
        BaseDao db=new BaseDao();
        md5 m=new md5();
        String sql="select * from company where cdkey=?";
        ResultSet re=db.query(sql,comp);
        re.last();
        if (re.getRow()==0){
            return "该邀请码无效";
        }
        re.close();
        sql="select * from users where username=?";
        ResultSet rs=db.query(sql,user.getName());
        rs.last();
        if (rs.getRow()>0){
            return "用户名存在了呢，建议换一个试试";
        }
        rs.close();
        sql="delete from company where cdkey=?";
        int num=db.update(sql,comp);
        if (num==0){
            return "参数不合法";
        }
        sql="insert into users (username,upassword, password, quota,quota_after,passroot) values (?,?,?,(select quota from config),0,?)";
        sha sha=new sha();
        String trojan_pwd=sha.encryptThisString(m.getMd5(user.getName()));
        String pwd_sha=sha.encryptThisString(user.getPwd());
        num=db.update(sql,user.getName(),pwd_sha,trojan_pwd,user);
        if (num==1){
            sql="insert into tokens (username) values(?)";
            db.update(sql,user.getName());
            sql="insert into questions (username,question,ans,settime) values(?,?,?,NOW())";
            db.update(sql,user.getName(),ques,sha.encryptThisString(ans));
            return "注册成功";
        }else {
            return "未知错误";
        }
    }
    public static String setQuestion(User user,String ques,String ans) throws SQLException {
        BaseDao db=new BaseDao();
        String pwd_sha=sha.encryptThisString(user.getPwd());
        String sql="select * from users where username=? and upassword=?";
        ResultSet rs=db.query(sql,user.getName(),pwd_sha);
        rs.last();
        if (rs.getRow()==0){
            return "pwd error";
        }
        sha sha=new sha();
        ans=sha.encryptThisString(ans);
        sql="insert into questions (username,question,ans,settime) values(?,?,?,NOW())";
        int num=db.update(sql,user.getName(),ques,ans);
        if (num==1){
            return "yes";
        }else {
            return "error";
        }
    }

    public static String getQuestion(String user) throws SQLException {
        BaseDao db=new BaseDao();
        String sql="select question from questions where username=?";
        ResultSet rs=db.query(sql,user);
        rs.last();
        if (rs.getRow()==0){
            return "user error";
        }else {
            return rs.getString("question");
        }
    }

    public static boolean checkQuestion(String user,String ans) throws SQLException {
        BaseDao db=new BaseDao();
        sha sha=new sha();
        ans=sha.encryptThisString(ans);
        String sql="select question from questions where username=? and ans=?";
        ResultSet rs=db.query(sql,user,ans);
        rs.last();
        if (rs.getRow()==0){
            return false;
        }else {
            return true;
        }
    }

    public static String setPwd(User user,String token) throws SQLException {
        BaseDao db=new BaseDao();
        String sql="update users set upassword=? where username=?";
        sha sha=new sha();
        String pwd_sha=sha.encryptThisString(user.getPwd());
        TokenDao dao=new TokenDao();
        if (dao.letToken(token,user.getName())){
            int num=db.update(sql,pwd_sha,user.getName());
            if (num==1){
                return "yes";
            }else {
                return "error";
            }
        }else {
            return "token error";
        }

    }
}
