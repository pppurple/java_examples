package servlet_container.servlet.http;

import servlet_container.servlet.ServletException;

import java.io.IOException;

public class HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
    }

    public void service(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        if (req.getMethod().equals("GET")) {
            doGet(req, res);
        } else if (req.getMethod().equals("POST")) {
            doPost(req, res);
        }
    }
}
