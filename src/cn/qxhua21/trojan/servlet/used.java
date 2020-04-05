package cn.qxhua21.trojan.servlet;

import cn.qxhua21.trojan.Dao.TokenDao;
import cn.qxhua21.trojan.Dao.dataUseDao;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "used",urlPatterns = "/used")
public class used extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* 允许跨域的主机地址 */
        response.setHeader("Access-Control-Allow-Origin", "*");
        /* 允许跨域的请求方法GET, POST, HEAD 等 */
        response.setHeader("Access-Control-Allow-Methods", "*");
        /* 重新预检验跨域的缓存时间 (s) */
        response.setHeader("Access-Control-Max-Age", "3600");
        /* 允许跨域的请求头 */
        response.setHeader("Access-Control-Allow-Headers", "*");
        /* 是否携带cookie */
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String user=request.getParameter("user");
        String token=request.getParameter("token");
        String type=request.getParameter("type");
        dataUseDao dao=new dataUseDao();
        PrintWriter out=response.getWriter();
        response.setContentType("text/html; charset=UTF-8");
        try {
            TokenDao tokenDao=new TokenDao();
            JSONObject json=new JSONObject();
            if (tokenDao.token(token,user)){
                if (type.equals("all")){
//                    json.put("max",dao.quota(user));
                    ArrayList<ArrayList<String>> date=dao.allUsed(user);
                    json.put("times",date.get(1));
                    json.put("useds",date.get(0));
                }else if (type.equals("mine")){
                    json.put("max",dao.quota(user));
                    String used=String.valueOf(dao.used(user));
                    json.put("used",dao.used(user));
                }
            }else {
                json.put("used","null");
            }
            out.print(json);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
