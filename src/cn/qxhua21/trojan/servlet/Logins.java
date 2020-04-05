package cn.qxhua21.trojan.servlet;

import cn.qxhua21.trojan.Dao.LoginDao;
import cn.qxhua21.trojan.Dao.getTokenDao;
import cn.qxhua21.trojan.lei.User;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "Logins",urlPatterns = "/Logins")
public class Logins extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        response.setContentType("text/html; charset=UTF-8");
        String name=request.getParameter("user");
        String pwd=request.getParameter("pwd");
        PrintWriter pw = response.getWriter();
        LoginDao dao=new LoginDao();
        User user=new User(name,pwd);
        JSONObject json=new JSONObject();
        try {
            if (dao.login(user)){
                if (dao.haveQuestion(user)){

                }else {
                    json.put("message","no question");
                }
                getTokenDao get=new getTokenDao();
                json.put("token",get.getToken(name));
            }else {
                json.put("error","null");
            }
            pw.print(json);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
