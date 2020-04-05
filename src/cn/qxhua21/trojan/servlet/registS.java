package cn.qxhua21.trojan.servlet;

import cn.qxhua21.trojan.Dao.registDao;
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

@WebServlet(name = "registS",urlPatterns = "/registS")
public class registS extends HttpServlet {
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
        String uname=request.getParameter("user");
        String pwd=request.getParameter("pwd");
        String com=request.getParameter("company");
        String question=request.getParameter("question");
        String ans=request.getParameter("ans");
        User user=new User(uname,pwd);
        registDao reg=new registDao();
        PrintWriter pw=response.getWriter();
        try {
            JSONObject json=new JSONObject();
            String message=reg.regist(user,com,question,ans);
            json.put("message",message);
            pw.print(json);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
