package tomcat_cookie;


import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CookieTest extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        String counterStr = null;

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            out.println("cookies == null");
        } else {
            out.println("cookies.length.." + cookies.length);
            int index = 0;
            for (Cookie c : cookies) {
                out.println("cookies[" + index + "].."
                        + cookies[index].getName() + "/" + cookies[index].getValue());
                if (cookies[index].getName().equals("COUNTER")) {
                    counterStr = cookies[index].getValue();
                }
                index++;
            }

            int counter;
            if (counterStr == null) {
                counter = 1;
            } else {
                counter = Integer.parseInt(counterStr) + 1;
            }
            Cookie newCookie = new Cookie("COUNTER", "" + counter);
            response.addCookie(newCookie);
        }
    }
}
