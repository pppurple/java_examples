package servlet_container.servletimpl;

import servlet_container.servlet.http.HttpServlet;
import servlet_container.servlet.http.HttpServletRequest;
import servlet_container.servlet.http.HttpServletResponse;
import servlet_container.util.Constants;
import servlet_container.util.SendResponse;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ServletService {
    private static HttpServlet createServlet(ServletInfo info) throws Exception {
        Class<?> clazz = info.webApp.classLoader.loadClass(info.servletClassName);
        return (HttpServlet)clazz.newInstance();
    }

    private static Map<String, String[]> stringToMap(String str) {
        Map<String, String[]> parameterMap =new HashMap<>();
        if (str != null) {
            String[] paramArray = str.split("&");
            for (String param : paramArray) {
                String[] keyValue = param.split("=");
                if (parameterMap.containsKey(keyValue[0])) {
                    String[] array = parameterMap.get(keyValue[0]);
                    String[] newArray = new String[array.length + 1];
                    System.arraycopy(array, 0, newArray, 0, array.length);
                    newArray[array.length] = keyValue[1];
                    parameterMap.put(keyValue[0], newArray);
                } else {
                    parameterMap.put(keyValue[0], new String[] {keyValue[1]});
                }
            }
        }
        return parameterMap;
    }

    private static String readToSize(InputStream input, int size) throws Exception {
        int ch;
        StringBuilder sb = new StringBuilder();
        int readSize = 0;

        while (readSize < size && (ch = input.read()) != -1) {
            sb.append((char)ch);
            readSize++;
        }
        return sb.toString();
    }

    public static void doService(String method, String query, ServletInfo info, Map<String, String> requestHeader,
                                 InputStream input, OutputStream output) throws Exception {
        if (info.servlet == null) {
            info.servlet = createServlet(info);
        }

        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
        HttpServletResponseImpl res = new HttpServletResponseImpl(outputBuffer);

        HttpServletRequest req;
        if (method.equals("GET")) {
            Map<String, String[]> map;
            map = stringToMap(query);
            req = new HttpServletRequestImpl("GET", map);
        } else if (method.equals("POST")) {
            int contentLength = Integer.parseInt(requestHeader.get("CONTENT-LENGTH"));
            Map<String, String[]> map;
            String line = readToSize(input, contentLength);
            map = stringToMap(line);
            req = new HttpServletRequestImpl("POST", map);
        } else {
            throw new AssertionError("BAD METHOD:" + method);
        }

        info.servlet.service(req, res);

        if (res.status == HttpServletResponse.SC_OK) {
            SendResponse.sendOkResponseHeader(output, res.contentType);
            res.printWriter.flush();
            byte[] outputBytes = outputBuffer.toByteArray();
            for (byte b : outputBytes) {
                output.write((int)b);
            }
        } else if (res.status == HttpServletResponse.SC_FOUND) {
            String redirectLocation;
            if (res.redirectLocation.startsWith("/")) {
                String host = requestHeader.get("HOST");
                redirectLocation = "http://"
                        + ((host != null) ? host : Constants.SERVER_NAME)
                        + res.redirectLocation;
            } else {
                redirectLocation = res.redirectLocation;
            }
            SendResponse.sendFoundResponse(output, redirectLocation);
        }
    }
}
