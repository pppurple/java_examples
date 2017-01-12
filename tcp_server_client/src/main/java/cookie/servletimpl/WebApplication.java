package cookie.servletimpl;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class WebApplication {
    private static String WEBAPPS_DIR = "";
    private static Map<String, WebApplication> webAppCollection = new HashMap<>();
    String directory;
    ClassLoader classLoader;
    private Map<String, ServletInfo> servletCollection = new HashMap<>();

    private WebApplication(String dir) throws MalformedURLException {
        this.directory = dir;
        FileSystem fs = FileSystems.getDefault();

        Path pathObj = fs.getPath(WEBAPPS_DIR + File.separator + dir);
        this.classLoader = URLClassLoader.newInstance(new URL[]{pathObj.toUri().toURL()});
    }

    public static WebApplication createInstance(String dir) throws MalformedURLException {
        WebApplication newApp = new WebApplication(dir);
        webAppCollection.put(dir, newApp);

        return newApp;
    }

    public void addServlet(String urlPattern, String servletClassName) {
        this.servletCollection.put(urlPattern, new ServletInfo(this, urlPattern, servletClassName));
    }

    public ServletInfo searchServlet(String path) {
        return servletCollection.get(path);
    }

    public static WebApplication searchWebApplication(String dir) {
        return webAppCollection.get(dir);
    }
}
