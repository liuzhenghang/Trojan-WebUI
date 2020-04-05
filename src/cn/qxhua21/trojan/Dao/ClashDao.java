package cn.qxhua21.trojan.Dao;

import cn.qxhua21.trojan.config.getConfig;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ClashDao {
    public static String clashDao(String passwd) throws Exception {
        String sql="select * from servers";
        BaseDao db=new BaseDao();
        ResultSet rs=db.query(sql);
        ArrayList<String> ProxyList=new ArrayList<>();
        ArrayList<String> serverList=new ArrayList<>();
        while (rs.next()){
            String server=rs.getString("name");
            String proxy="- name: \""+rs.getString("name")+"\"\n" +
                    "  type: "+rs.getString("type")+"\n" +
                    "  server: "+rs.getString("addr")+"\n" +
                    "  port: "+rs.getString("port")+"\n" +
                    "  password: "+passwd+"\n";
            serverList.add(server);
            ProxyList.add(proxy);
        }
        String Servers="";
        for (int i=0;i<serverList.size();i++){
            Servers+="    - \""+serverList.get(i)+"\"\n";
        }
        String Proxys="";
        for (int i=0;i<ProxyList.size();i++){
            Proxys+=ProxyList.get(i);
        }
        getConfig clash=new getConfig();
        String cfg=clash.fileRead("clashUrl.yaml");
        cfg=cfg.replace("#$Trojan",Proxys);
        cfg=cfg.replace("#$ServersName",Servers);
        return cfg;

    }
}
