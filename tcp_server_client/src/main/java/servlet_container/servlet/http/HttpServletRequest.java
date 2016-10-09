package servlet_container.servlet.http;

import java.io.UnsupportedEncodingException;

public interface HttpServletRequest {
    String getMethod();
    String getParameter(String name);
    String[] getParameterValues(String name);
    void setCharacterEncoding(String env) throws UnsupportedEncodingException;
}
