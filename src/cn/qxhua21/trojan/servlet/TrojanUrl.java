package cn.qxhua21.trojan.servlet;

import cn.qxhua21.trojan.Dao.TokenDao;
import cn.qxhua21.trojan.Dao.UrlDao;
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

@WebServlet(name = "TrojanUrl",urlPatterns = "/TrojanUrl")
public class TrojanUrl extends HttpServlet {
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
        StringBuffer urls = request.getRequestURL();
//        String tempContextUrl = urls.delete(urls.length() - request.getRequestURI().length(), urls.length()).append("/").toString();
        String uuu=urls.toString();
        String user=request.getParameter("user");
        String token=request.getParameter("token");
        dataUseDao dao=new dataUseDao();
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out=response.getWriter();
        try {
            TokenDao tokenDao=new TokenDao();
            JSONObject json=new JSONObject();
            if (tokenDao.token(token,user)){

                UrlDao url=new UrlDao();

                ArrayList<ArrayList> turl=url.trojan_url(user);
                json.put("type",turl.get(0));
                json.put("address",turl.get(1));
                json.put("url",turl.get(2));
                json.put("bandwidth",turl.get(3));
                json.put("clash",url.clashURL(user,uuu));
            }else {
                json.put("url","null");

            }
            out.print(json);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
