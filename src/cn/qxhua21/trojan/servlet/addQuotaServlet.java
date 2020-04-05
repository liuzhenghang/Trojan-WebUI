package cn.qxhua21.trojan.servlet;

import cn.qxhua21.trojan.Dao.AddQuotaDao;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "addQuotaServlet",urlPatterns = "/addQuotaServlet")
public class addQuotaServlet extends HttpServlet {
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
        String cdk=request.getParameter("cdk");
        String user=request.getParameter("user");
        AddQuotaDao add=new AddQuotaDao();
        try {
            String message=add.add(cdk,user);
            PrintWriter out=response.getWriter();
            JSONObject json=new JSONObject();
            json.put("message",message);
            out.print(json);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
