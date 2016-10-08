package bbs;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class PostBBS extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws UnsupportedEncodingException, IOException {
        request.setCharacterEncoding("UTF-8");
        Message newMessage = new Message(request.getParameter("title"),
                request.getParameter("handle"),
                request.getParameter("message"));
        Message.messageList.add(0, newMessage);
        response.sendRedirect("/testbbs_jsp/showbbs.jsp");
    }
}
