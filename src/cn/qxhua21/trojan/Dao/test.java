package cn.qxhua21.trojan.Dao;

import cn.qxhua21.trojan.sha.md5;
import cn.qxhua21.trojan.sha.sha;
import sun.security.provider.MD5;

import java.sql.SQLException;

public class test {
    public static void main(String[] args) throws SQLException {
//        for (int i=0;i<3;i++){
////            String num=String.valueOf(Math.random());
            String num="977bd158c3940154376e98325847809f";
//            sha sha=new sha();
            String name=num;
//            String s=sha.encryptThisString(sha.encryptThisString(name));
            System.out.println("一次"+sha.encryptThisString(name));
//            System.out.println("两次"+s);
//        }
        md5 m=new md5();
        System.out.println(m.getMd5("2020-04-04 18:40:14"));
        System.out.println(m.getMd5("2020-04-04 18:40:15"));
        System.out.println(m.getMd5("2020-04-04 18:40:16"));
        System.out.println(m.getMd5("2020-04-04 18:40:17"));
        System.out.println(m.getMd5("2020-04-04 18:40:18"));
    }
}
