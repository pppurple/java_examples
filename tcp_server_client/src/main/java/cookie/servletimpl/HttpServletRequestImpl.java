package cookie.servletimpl;

import cookie.servlet.http.Cookie;
import cookie.util.URLDecoder;
import cookie.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;

public class HttpServletRequestImpl implements HttpServletRequest {
    private String method;
    private String characterEncoding = "ISO-8859-1";
    private Map<String, String[]> parameterMap;
    private Cookie[] cookies;

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public String getParameter(String name) {
        String[] values = getParameterValues(name);
        if (values == null) {
            return null;
        }
        return values[0];
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = this.parameterMap.get(name);
        if (values == null) {
            return null;
        }
        String[] decoded = new String[values.length];
        try {
            for (int i = 0; i < values.length; i++) {
                decoded[i] = URLDecoder.decode(values[i], this.characterEncoding);
            }
        } catch (UnsupportedEncodingException e) {
            throw  new AssertionError(e);
        }
        return decoded;
    }

    @Override
    public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
        if (!Charset.isSupported(env)) {
            throw new UnsupportedEncodingException("encoding.." + env);
        }
        this.characterEncoding = env;
    }

    HttpServletRequestImpl(String method, Map<String, String> requestHeader, Map<String, String[]> parameterMap) {
        this.method = method;
        this.parameterMap = parameterMap;
        this.cookies = parseCookies(requestHeader.get("COOKIE"));
    }

    @Override
    public Cookie[] getCookies() {
        return this.cookies;
    }

    private static Cookie[] parseCookies(String cookieString) {
        if (cookieString == null) {
            return null;
        }
        String[] cookiePairArray = cookieString.split(";");
        Cookie[] ret = new Cookie[cookiePairArray.length];
        int cookieCount = 0;

        for (String cookiePair : cookiePairArray) {
            String[] pair = cookiePair.split("=", 2);

            ret[cookieCount] = new Cookie(pair[0], pair[1]);
            cookieCount++;
        }
        return ret;
    }
}
