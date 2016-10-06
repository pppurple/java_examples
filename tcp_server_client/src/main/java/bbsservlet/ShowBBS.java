package bbsservlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ShowBBS extends HttpServlet {
    private String escapeHtml(String src) {
        return src.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>test bbsservlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>test BBS</h1>");
        out.println("<form action='/testbbs/PostBBS' method='post'>");
        out.println("title : <input type='text' name='title' size='60'><br/>");
        out.println("handle name : <input type='text' name='handle'><br/>");
        out.println("<textarea name='message' rows='4' cols='60'></textarea><br/>");
        out.println("<input type='submit'/>");
        out.println("</form>");
        out.println("<hr/>");

        Message.messageList.forEach(msg -> {
            out.println("<p>「" + escapeHtml(msg.title) + "」&nbsp;&nbsp;"
                    + escapeHtml(msg.handle) + " さん&nbsp;&nbsp;"
                    + escapeHtml(msg.date.toString()) + "</p>");
            out.println("<p>");
            out.println(escapeHtml(msg.message).replace("\r\n", "<br/>"));
            out.println("</p><hr/>");
        });

        out.println("</body>");
        out.println("</html>");
    }
}
